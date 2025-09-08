package com.polynomeer.excelexport;

interface ExportQueue {
    void enqueue(ExportJob job);
}