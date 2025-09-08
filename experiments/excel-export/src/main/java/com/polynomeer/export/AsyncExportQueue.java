package com.polynomeer.export;

class AsyncExportQueue implements ExportQueue {
    private final ExportWorker worker;

    AsyncExportQueue(ExportWorker worker) {
        this.worker = worker;
    }

    @Override
    public void enqueue(ExportJob job) {
        worker.process(job); // fire-and-forget async
    }
}