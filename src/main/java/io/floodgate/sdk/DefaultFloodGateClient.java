package io.floodgate.sdk;

import io.floodgate.sdk.services.FeatureFlagService;

class DefaultFloodGateClient implements FloodGateClient {
    private final FeatureFlagService flagService;

    public DefaultFloodGateClient(FeatureFlagService flagService) {
        this.flagService = flagService;
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
