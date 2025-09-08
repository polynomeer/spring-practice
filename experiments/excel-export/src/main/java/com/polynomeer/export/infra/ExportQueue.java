package com.polynomeer.export.infra;

import com.polynomeer.export.domain.ExportJob;

public interface ExportQueue {
    void enqueue(ExportJob job);
}