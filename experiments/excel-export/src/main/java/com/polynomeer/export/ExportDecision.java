package com.polynomeer.export;

record ExportDecision(Type type, String cachedUrl, String reason, long predictedSeconds) {
    enum Type {SERVE_CACHE, QUEUE, REJECT_IMPOSSIBLE}
}