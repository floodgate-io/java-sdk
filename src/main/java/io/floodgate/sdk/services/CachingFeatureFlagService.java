package io.floodgate.sdk.services;

import io.floodgate.sdk.caching.Constants;
import io.floodgate.sdk.caching.SimpleMemoryCache;
import io.floodgate.sdk.models.FeatureFlag;

import java.util.Map;
import java.util.Optional;

public class CachingFeatureFlagService implements FeatureFlagService {
    private final SimpleMemoryCache cache;
    private final FeatureFlagService inner;

    private static final System.Logger log = System.getLogger(CachingFeatureFlagService.class.getName());

    public CachingFeatureFlagService(SimpleMemoryCache cache, FeatureFlagService inner) {
        this.cache = cache;
        this.inner = inner;
    }

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {

        var cachedResults = cache.get(Constants.FEATURE_FLAG_CACHE_KEY);

        if (cachedResults != null) {
            log.log(System.Logger.Level.DEBUG, "Cache hit for {}", Constants.FEATURE_FLAG_CACHE_KEY);
            return (Optional<Map<String, FeatureFlag>>) cachedResults;
        }

        log.log(System.Logger.Level.DEBUG, "Cache miss for {}", Constants.FEATURE_FLAG_CACHE_KEY);
            var results = inner.getFlags();

        if (results.isPresent()) {
            cache.set(Constants.FEATURE_FLAG_CACHE_KEY, results);
        }

        return results;
    }
}
