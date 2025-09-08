package com.polynomeer.export.infra;

import com.polynomeer.export.web.ExportRequest;

public interface ExcelGenerator {
    byte[] generate(ExportRequest request) throws Exception;
}
