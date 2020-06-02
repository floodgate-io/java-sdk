package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.caching.DefaultMemoryCache;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.io.FileReader;

public class FeatureFlagServiceFactory {
    public static CachingFeatureFlagService create(ClientConfig config) {
        var mapper = new ObjectMapper();

        var fileSystem = new FileSystemFeatureFlagService(config, new FileReader(), mapper);
        var cdn = new CdnFeatureFlagService(config, mapper);

        var mux = new MultiplexingFeatureFlagService(fileSystem, cdn);

        var cache = new DefaultMemoryCache();
        return new CachingFeatureFlagService(cache, mux);
    }
}
