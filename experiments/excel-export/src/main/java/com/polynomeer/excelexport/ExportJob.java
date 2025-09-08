package com.polynomeer.excelexport;

import java.time.Instant;
import java.util.UUID;

class ExportJob {
    public final String id = UUID.randomUUID().toString();
    public final ExportRequest request;
    public final Instant createdAt = Instant.now();
    public volatile JobStatus status = JobStatus.PENDING;
    public volatile String s3Url; // set on success
    public volatile String error;

    ExportJob(ExportRequest request) {
        this.request = request;
    }
}