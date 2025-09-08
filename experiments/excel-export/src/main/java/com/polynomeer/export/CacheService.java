package com.polynomeer.export;

import java.time.Duration;

interface CacheService {
    /**
     * Return S3 URL if cached, else null
     */
    String get(String key);

    void put(String key, String url, Duration ttl);
}