package com.polynomeer.export;

interface ExportQueue {
    void enqueue(ExportJob job);
}