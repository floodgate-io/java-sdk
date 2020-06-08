package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.caching.DefaultMemoryCache;
import io.floodgate.sdk.config.FloodGateClientConfig;
import io.floodgate.sdk.io.FileReader;

/**
 * Eases instantiation of default FeatureFlagService setup.
 *
 * By default an instance is returned which will
 * <ul>
 *     <li>Check for flags from a local feature flag file</li>
 *     <li>Fallback to checking the FloodGate API</li>
 *     <li>Cache the results</li>
 * </ul>
 *
 */
public class FeatureFlagServiceFactory {
    public static CachingFeatureFlagService create(FloodGateClientConfig config) {
        var mapper = new ObjectMapper();
        var cache = new DefaultMemoryCache();

        var fileSystem = new FileSystemFeatureFlagService(config, new FileReader(), mapper);
        var cdn = new CdnFeatureFlagService(config, mapper, cache);

        var mux = new MultiplexingFeatureFlagService(fileSystem, cdn);

        return new CachingFeatureFlagService(cache, mux);
    }
}
