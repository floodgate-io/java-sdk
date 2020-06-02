package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.caching.DefaultMemoryCache;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.io.FileReader;

public class FeatureFlagServiceFactory {
    public static CachingFeatureFlagService create(ClientConfig config) {
        var mapper = new ObjectMapper();
        var cache = new DefaultMemoryCache();

        var fileSystem = new FileSystemFeatureFlagService(config, new FileReader(), mapper);
        var cdn = new CdnFeatureFlagService(config, mapper, cache);

        var mux = new MultiplexingFeatureFlagService(fileSystem, cdn);


        return new CachingFeatureFlagService(cache, mux);
    }
}
