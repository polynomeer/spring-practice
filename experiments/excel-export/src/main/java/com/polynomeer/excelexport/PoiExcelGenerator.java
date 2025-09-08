package com.polynomeer.excelexport;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

class PoiExcelGenerator implements ExcelGenerator {
    @Override
    public byte[] generate(ExportRequest request) throws Exception {
        // Minimal Apache POI implementation to keep sample self-contained without external deps.
        // In production, use org.apache.poi.xssf.usermodel.* to build a real workbook.
        // Here we emit a CSV but with .xlsx key for brevity; replace with real POI.
        String header = "id,name,date,region,category,status\n";
        String data = "1,Alice,2025-09-01,APAC,A,OPEN\n2,Bob,2025-09-02,EMEA,B,CLOSED\n";
        String meta = "# filters=" + (request.filters() == null ? "{}" : request.filters()) + "\n";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(meta.getBytes(StandardCharsets.UTF_8));
        bos.write(header.getBytes(StandardCharsets.UTF_8));
        bos.write(data.getBytes(StandardCharsets.UTF_8));
        return bos.toByteArray();
    }
}