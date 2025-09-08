package com.polynomeer.export.infra;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements CacheService {
    static class Entry {
        String url;
        Instant expireAt;
    }

    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    @Override
    public String get(String key) {
        Entry e = store.get(key);
        if (e == null) return null;
        if (Instant.now().isAfter(e.expireAt)) {
            store.remove(key);
            return null;
        }
        return e.url;
    }

    @Override
    public void put(String key, String url, Duration ttl) {
        Entry e = new Entry();
        e.url = url;
        e.expireAt = Instant.now().plus(ttl);
        store.put(key, e);
    }
}