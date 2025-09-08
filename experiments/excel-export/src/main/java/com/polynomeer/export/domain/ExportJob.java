package com.polynomeer.export.domain;

import com.polynomeer.export.web.ExportRequest;

import java.time.Instant;
import java.util.UUID;

public class ExportJob {
    public final String id = UUID.randomUUID().toString();
    public final ExportRequest request;
    public final Instant createdAt = Instant.now();
    public volatile JobStatus status = JobStatus.PENDING;
    public volatile String s3Url; // set on success
    public volatile String error;

    public ExportJob(ExportRequest request) {
        this.request = request;
    }
}