package com.polynomeer.export;

interface ExcelGenerator {
    byte[] generate(ExportRequest request) throws Exception;
}
