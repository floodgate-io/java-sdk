package io.floodgate.sdk;

import io.floodgate.sdk.caching.DefaultMemoryCache;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.services.FeatureFlagServiceFactory;

public class FloodGateClientFactory {
    /**
     * Create a new instance of FloodGate client and setup default configuration
     *
     * @param apiKey FloodGate environment API Key
     * @return Configured FloodGate client instance
     */
    public static FloodGateClient create(String apiKey) {
        var config = new ClientConfig(apiKey);
        var cache = new DefaultMemoryCache();
        var featureFlagService = FeatureFlagServiceFactory.create(config);

        return new DefaultFloodGateClient(config, featureFlagService, cache);
    }

    public static FloodGateClient create(ClientConfig config) {
        var featureFlagService = FeatureFlagServiceFactory.create(config);
        var cache = new DefaultMemoryCache();

        return new DefaultFloodGateClient(config, featureFlagService, cache);
    }
}
