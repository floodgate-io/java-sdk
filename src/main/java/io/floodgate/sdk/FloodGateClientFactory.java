package io.floodgate.sdk;

import io.floodgate.sdk.config.FloodGateClientConfig;
import io.floodgate.sdk.services.FeatureFlagService;
import io.floodgate.sdk.services.FeatureFlagServiceFactory;

/**
 * Provides factory methods to ease instantiation of FloodGateClient.
 */
public class FloodGateClientFactory {
    /**
     * Create a new instance of FloodGate client with default configuration.
     *
     * <p>
     * This will create a client that will cache flags from the API and auto update in
     * a background timer thread every 60 seconds.
     *
     * <p>
     * Please use one of the other factory methods for more control over the configuration.
     *
     * @param apiKey FloodGate environment API Key
     * @return Configured FloodGateClient instance
     */
    public static FloodGateClient create(String apiKey) {
        var config = new FloodGateClientConfig(apiKey);
        var featureFlagService = FeatureFlagServiceFactory.create(config);

        return new DefaultFloodGateClient(config, featureFlagService);
    }

    /**
     * Create a new instance of FloodGate client with user specified configuration.
     *
     * @param config User specified configuration
     * @return Configured FloodGateClient instance
     */
    public static FloodGateClient create(FloodGateClientConfig config) {
        var featureFlagService = FeatureFlagServiceFactory.create(config);

        return new DefaultFloodGateClient(config, featureFlagService);
    }

    /**
     * Create a new instance of FloodGate client with user specified configuration and explicit feature flag service.
     *
     * <p>
     * This is provided in case you have more complex requirements, perhaps you wish to cache in Redis or use an
     * alternate data source.
     *
     * @param config User specified configuration
     * @param service User provided feature flag service for more bespoke setups.
     * @return Configured FloodGateClient instance
     */
    public static FloodGateClient create(FloodGateClientConfig config, FeatureFlagService service) {
        return new DefaultFloodGateClient(config, service);
    }
}
