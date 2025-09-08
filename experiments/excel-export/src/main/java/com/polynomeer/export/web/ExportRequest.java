package com.polynomeer.export.web;

import java.util.Map;

public record ExportRequest(
        Map<String, String> filters,
        String requestedBy
) {
    public boolean isFullExport() {
        return filters == null || filters.isEmpty();
    }
}