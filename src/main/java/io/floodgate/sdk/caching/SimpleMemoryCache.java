package io.floodgate.sdk.caching;

public interface SimpleMemoryCache {
    void set(String key, Object value);
    Object get(String key);
    void invalidate(String key);
}
