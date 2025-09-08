package com.polynomeer.excelexport;

import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class PreGeneratedService {
    private final ExcelGenerator generator;
    private final S3Storage storage;
    private volatile String latestFullUrl; // most recent successful URL
    private volatile Instant latestFullGeneratedAt;


    PreGeneratedService(ExcelGenerator generator, S3Storage storage) {
        this.generator = generator;
        this.storage = storage;
    }


    /**
     * Return URL if generated within the last N hours (fresh), else null
     */
    public String getFreshFullExportUrl() {
        if (latestFullUrl == null) return null;
        if (latestFullGeneratedAt == null) return null;
        if (Instant.now().isBefore(latestFullGeneratedAt.plus(Duration.ofHours(6)))) {
            return latestFullUrl;
        }
        return null;
    }


    public void registerFullExportUrl(String url) {
        this.latestFullUrl = url;
        this.latestFullGeneratedAt = Instant.now();
    }


    @Async("exportExecutor")
    public CompletableFuture<Void> ensureFullExportRefreshAsync() {
        try {
            // Idempotent guard: if a fresh file exists, skip
            if (getFreshFullExportUrl() != null) return CompletableFuture.completedFuture(null);
            byte[] bytes = generator.generate(new ExportRequest(Map.of(), "system-pre-gen"));
            String key = "full/" + LocalDate.now() + "/pre_generated-" + Instant.now().toEpochMilli() + ".xlsx";
            String url = storage.upload("exports-bucket", key, bytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            registerFullExportUrl(url);
        } catch (Exception ignored) {
        }
        return CompletableFuture.completedFuture(null);
    }
}