package com.polynomeer.export.app;

import com.polynomeer.export.domain.ExportJob;
import com.polynomeer.export.infra.ExportQueue;
import com.polynomeer.export.web.ExportRequest;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExportCoordinator {
    private final ExportDecisionService decisionService;
    private final PreGeneratedService preGeneratedService;
    private final ExportQueue queue;
    private final Map<String, ExportJob> jobs = new ConcurrentHashMap<>();

    public ExportCoordinator(ExportDecisionService decisionService, PreGeneratedService preGeneratedService, ExportQueue queue) {
        this.decisionService = decisionService;
        this.preGeneratedService = preGeneratedService;
        this.queue = queue;
    }

    public Map<String, Object> handleExportRequest(ExportRequest req) {
        // Branch 1: Full export (no filters) → serve pre-generated S3 file if fresh
        if (req.isFullExport()) {
            String url = preGeneratedService.getFreshFullExportUrl();
            if (url != null) {
                return Map.of(
                        "mode", "PRE_GENERATED",
                        "downloadUrl", url,
                        "message", "Served from pre-generated full export on S3"
                );
            }
            // If not fresh, trigger (idempotent) creation in background and enqueue a regular job as fallback
            preGeneratedService.ensureFullExportRefreshAsync();
            ExportJob job = new ExportJob(req);
            jobs.put(job.id, job);
            queue.enqueue(job);
            return Map.of(
                    "mode", "QUEUED",
                    "jobId", job.id,
                    "message", "Full export refresh in progress; job queued as fallback"
            );
        }

        // Branch 2: Filtered export → decide cache vs queue via predictive model
        ExportDecision decision = decisionService.decide(req);
        if (decision.type() == ExportDecision.Type.SERVE_CACHE) {
            return Map.of(
                    "mode", "CACHE",
                    "downloadUrl", decision.cachedUrl(),
                    "message", "Served from cache based on predicted cost"
            );
        }
        if (decision.type() == ExportDecision.Type.REJECT_IMPOSSIBLE) {
            return Map.of("mode", "REJECTED", "reason", decision.reason());
        }
        // QUEUE flow
        ExportJob job = new ExportJob(req);
        jobs.put(job.id, job);
        queue.enqueue(job);
        return Map.of(
                "mode", "QUEUED",
                "jobId", job.id,
                "predictedSeconds", decision.predictedSeconds(),
                "message", "Queued based on predicted cost"
        );
    }

    public Optional<ExportJob> getJob(String id) {
        return Optional.ofNullable(jobs.get(id));
    }
}