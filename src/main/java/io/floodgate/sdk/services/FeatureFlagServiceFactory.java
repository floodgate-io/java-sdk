package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.io.FileReader;

public class FeatureFlagServiceFactory {
    public static FeatureFlagService create(ClientConfig config) {
        var mapper = new ObjectMapper();

        var fileSystem = new FileSystemFeatureFlagService(config, new FileReader(), mapper);
        var cdn = new CdnFeatureFlagService(config, mapper);

        var mux = new MultiplexingFeatureFlagService(fileSystem, cdn);

        // TODO: Caching FeatureFlagService

        return mux;
    }
}
