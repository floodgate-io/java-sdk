package io.floodgate.sdk;

import io.floodgate.sdk.config.FloodgateClientConfig;
import io.floodgate.sdk.services.FeatureFlagService;
import io.floodgate.sdk.services.FeatureFlagServiceFactory;

/**
 * Provides factory methods to ease instantiation of Floodgate.
 */
public class FloodgateClientFactory {
    /**
     * Create a new instance of Floodgate client with default configuration.
     *
     * <p>
     * This will create a client that will cache flags from the API and auto update in
     * a background timer thread every 60 seconds.
     *
     * <p>
     * Please use one of the other factory methods for more control over the configuration.
     *
     * @param apiKey Floodgate environment API Key
     * @return Configured FloodgateClient instance
     */
    public static FloodgateClient create(String apiKey) {
        var config = new FloodgateClientConfig(apiKey);
        var featureFlagService = FeatureFlagServiceFactory.create(config);

        return new DefaultFloodgateClient(config, featureFlagService);
    }

    /**
     * Create a new instance of Floodgate client with user specified configuration.
     *
     * @param config User specified configuration
     * @return Configured FloodgateClient instance
     */
    public static FloodgateClient create(FloodgateClientConfig config) {
        var featureFlagService = FeatureFlagServiceFactory.create(config);

        return new DefaultFloodgateClient(config, featureFlagService);
    }

    /**
     * Create a new instance of Floodgate client with user specified configuration and explicit feature flag service.
     *
     * <p>
     * This is provided in case you have more complex requirements, perhaps you wish to cache in Redis or use an
     * alternate data source.
     *
     * @param config User specified configuration
     * @param service User provided feature flag service for more bespoke setups.
     * @return Configured FloodgateClient instance
     */
    public static FloodgateClient create(FloodgateClientConfig config, FeatureFlagService service) {
        return new DefaultFloodgateClient(config, service);
    }
}
