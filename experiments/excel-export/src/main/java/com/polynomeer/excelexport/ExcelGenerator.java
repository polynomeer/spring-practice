package com.polynomeer.excelexport;

interface ExcelGenerator {
    byte[] generate(ExportRequest request) throws Exception;
}
