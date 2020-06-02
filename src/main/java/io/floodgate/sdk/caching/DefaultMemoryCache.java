package io.floodgate.sdk.caching;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void invalidate(String key) {
        synchronized (cache) {
            logger.log(System.Logger.Level.DEBUG, "Invalidating for key {}", key);
            cache.remove(key);
        }
    }
}
