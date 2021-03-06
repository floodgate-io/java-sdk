package io.floodgate.sdk.caching;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple cache to store arbitrary data under an associated key in memory.
 */
public class DefaultMemoryCache implements SimpleMemoryCache {
    private static Map<String, Object> cache = new HashMap<>();

    private static final System.Logger logger = System.getLogger(DefaultMemoryCache.class.getName());

    @Override
    public void set(String key, Object value) {
        synchronized (cache) {
            logger.log(System.Logger.Level.DEBUG, "Storing {} for key {}", value, key);
            cache.put(key, value);
        }
    }

    @Override
    public Object get(String key) {
        synchronized (cache) {
            logger.log(System.Logger.Level.DEBUG, "get key {}", key);
            return cache.get(key);
        }
    }
}
