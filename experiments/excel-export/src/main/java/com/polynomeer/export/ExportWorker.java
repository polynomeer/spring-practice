package com.polynomeer.export;

import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class ExportWorker {
    private final ExcelGenerator generator;
    private final S3Storage storage;
    private final CacheService cache;
    private final PreGeneratedService preGeneratedService;

    ExportWorker(ExcelGenerator generator, S3Storage storage, CacheService cache, PreGeneratedService preGeneratedService) {
        this.generator = generator;
        this.storage = storage;
        this.cache = cache;
        this.preGeneratedService = preGeneratedService;
    }

    @Async("exportExecutor")
    public CompletableFuture<Void> process(ExportJob job) {
        job.status = JobStatus.RUNNING;
        try {
            byte[] bytes;
            if (job.request.isFullExport()) {
                // As a safety, also rely on the pre-generated pathway
                String url = preGeneratedService.getFreshFullExportUrl();
                if (url != null) {
                    job.s3Url = url;
                    job.status = JobStatus.SUCCEEDED;
                    return CompletableFuture.completedFuture(null);
                }
            }

            // Generate on the fly
            bytes = generator.generate(job.request);
            String key = s3Key(job.request);
            String url = storage.upload("exports-bucket", key, bytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            job.s3Url = url;

            // Cache filtered results for a short TTL (e.g., 15 min)
            if (!job.request.isFullExport()) {
                cache.put(cacheKey(job.request.filters()), url, Duration.ofMinutes(15));
            } else {
                preGeneratedService.registerFullExportUrl(url); // update registry if this was a full export
            }

            job.status = JobStatus.SUCCEEDED;
        } catch (Exception e) {
            job.status = JobStatus.FAILED;
            job.error = e.getMessage();
        }
        return CompletableFuture.completedFuture(null);
    }

    private String cacheKey(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) return "FULL";
        return filters.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("|"));
    }

    private String s3Key(ExportRequest req) {
        String ts = String.valueOf(Instant.now().toEpochMilli());
        if (req.isFullExport()) return "full/" + LocalDate.now() + "/export-" + ts + ".xlsx";
        String filters = req.filters().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "_" + sanitize(e.getValue()))
                .collect(Collectors.joining("__"));
        return "filtered/" + LocalDate.now() + "/" + filters + "-" + ts + ".xlsx";
    }

    private String sanitize(String s) {
        return s.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}