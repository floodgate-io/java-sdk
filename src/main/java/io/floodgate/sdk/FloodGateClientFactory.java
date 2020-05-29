package io.floodgate.sdk;

import io.floodgate.sdk.config.ClientConfig;

public class FloodGateClientFactory {
    /**
     * Create a new instance of FloodGate client and setup default configuration
     *
     * @param apiKey FloodGate environment API Key
     * @return Configured FloodGate client instance
     */
    public static FloodGateClient create(String apiKey) {
        var config = new ClientConfig(apiKey);
        return new DefaultFloodGateClient(config);
    }
}
