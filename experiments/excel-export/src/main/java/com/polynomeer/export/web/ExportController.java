package com.polynomeer.export.web;

import com.polynomeer.export.app.ExportCoordinator;
import com.polynomeer.export.domain.ExportJob;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exports")
class ExportController {
    private final ExportCoordinator coordinator;

    ExportController(ExportCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestExport(@RequestBody ExportRequest req) {
        return ResponseEntity.ok(coordinator.handleExportRequest(req));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<?> getStatus(@PathVariable String jobId) {
        Optional<ExportJob> job = coordinator.getJob(jobId);
        if (job.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Job not found"));
        ExportJob j = job.get();
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("id", j.id);
        resp.put("status", j.status);
        resp.put("createdAt", j.createdAt.toString());
        if (j.s3Url != null) resp.put("downloadUrl", j.s3Url);
        if (j.error != null) resp.put("error", j.error);
        return ResponseEntity.ok(resp);
    }
}