package com.polynomeer.export;

import java.util.Map;

record ExportRequest(
        Map<String, String> filters,
        String requestedBy
) {
    boolean isFullExport() {
        return filters == null || filters.isEmpty();
    }
}