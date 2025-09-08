package com.polynomeer.export.infra;

import java.time.Duration;

public interface CacheService {
    /**
     * Return S3 URL if cached, else null
     */
    String get(String key);

    void put(String key, String url, Duration ttl);
}