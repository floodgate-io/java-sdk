package io.floodgate.sdk.caching;

public interface SimpleMemoryCache {
    /**
     * @param key   Key to associate with payload
     * @param value Payload that is found by Key
     */
    void set(String key, Object value);

    /**
     * @param key Key used to locate payload
     * @return Payload if present, otherwise null
     */
    Object get(String key);
}
