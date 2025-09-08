package com.polynomeer.export.infra;

import com.polynomeer.export.domain.ExportJob;

public class AsyncExportQueue implements ExportQueue {
    private final ExportWorker worker;

    public AsyncExportQueue(ExportWorker worker) {
        this.worker = worker;
    }

    @Override
    public void enqueue(ExportJob job) {
        worker.process(job); // fire-and-forget async
    }
}