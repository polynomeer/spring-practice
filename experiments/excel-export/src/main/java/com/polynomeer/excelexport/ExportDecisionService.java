package com.polynomeer.excelexport;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class ExportDecisionService {
    private final CacheService cache;
    private final Map<String, Integer> fieldWeights; // e.g., heavy joins, cardinality, etc.
    private final long queueThresholdSec; // if predicted > threshold, queue; else serve cache if available

    ExportDecisionService(CacheService cache) {
        this.cache = cache;
        this.fieldWeights = defaultWeights();
        this.queueThresholdSec = 8; // tune per environment
    }

    public ExportDecision decide(ExportRequest req) {
        // Normalize key for caching
        String cacheKey = cacheKey(req.filters());
        String cached = cache.get(cacheKey);
        long predicted = predictSeconds(req.filters());

        if (cached != null) {
            // If we already cached, serve from cache when predicted cost is non-trivial
            if (predicted >= 2) return new ExportDecision(ExportDecision.Type.SERVE_CACHE, cached, null, predicted);
        }

        if (predicted > 3600) {
            return new ExportDecision(ExportDecision.Type.REJECT_IMPOSSIBLE, null, "Requested slice is too large; refine filters", predicted);
        }

        return new ExportDecision(ExportDecision.Type.QUEUE, null, null, predicted);
    }

    private String cacheKey(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) return "FULL";
        return filters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("|"));
    }

    private long predictSeconds(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) return 3; // full export is pre-generated; fallback cost small
        int score = 0;
        for (Map.Entry<String, String> e : filters.entrySet()) {
            score += fieldWeights.getOrDefault(e.getKey(), 1);
            // heuristic: wildcards or large ranges are heavier
            if (StringUtils.hasText(e.getValue()) && (e.getValue().contains("*") || e.getValue().contains(",")))
                score += 2;
            if (e.getValue().matches(".*\\d{4}-\\d{2}-\\d{2}.*")) score += 1; // date filters
        }
        // Convert score → seconds via simple mapping (tune with real telemetry)
        return Math.max(1, score * 2L);
    }

    private Map<String, Integer> defaultWeights() {
        Map<String, Integer> w = new HashMap<>();
        // Example: heavier fields
        w.put("dateRange", 4);
        w.put("category", 2);
        w.put("region", 3);
        w.put("customerId", 5);
        w.put("status", 1);
        return w;
    }
}