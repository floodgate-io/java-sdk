package io.floodgate.sdk;

import io.floodgate.sdk.models.FeatureFlag;
import io.floodgate.sdk.services.FeatureFlagService;

import java.util.Optional;

class DefaultFloodGateClient implements FloodGateClient {
    private final FeatureFlagService flagService;

    public DefaultFloodGateClient(FeatureFlagService flagService) {
        this.flagService = flagService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getValue(String key, boolean defaultValue, User user) {
        var flag = getFlag(key, user);

        if(flag.isEmpty())
            return defaultValue;

        return Boolean.valueOf(flag.get().value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flushEvents() {
        throw new UnsupportedOperationException();
    }


    private Optional<FeatureFlag> getFlag(String key, User user) {

        // TODO: user targeting from config user
        // TODO: user targeting from override user

        var flags = flagService.getFlags();

        if(flags.isEmpty())
            return Optional.empty();

        var flag = flags.get().get(key);
        if(flag == null)
            return Optional.empty();

        return Optional.of(flag);
    }
}
