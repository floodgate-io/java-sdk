package io.floodgate.sdk;

import io.floodgate.sdk.config.ClientConfig;

class DefaultFloodGateClient implements FloodGateClient {
    private final ClientConfig config;

    public DefaultFloodGateClient(ClientConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getValue(String key, boolean defaultValue) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flushEvents() {
        throw new UnsupportedOperationException();
    }
}
