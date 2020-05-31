package io.floodgate.sdk.services;

import io.floodgate.sdk.models.FeatureFlag;

import java.util.Map;
import java.util.Optional;

public class MultiplexingFeatureFlagService implements FeatureFlagService {

    private FeatureFlagService[] services;

    public MultiplexingFeatureFlagService(FeatureFlagService...services) {
        this.services = services;
    }

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        for (var service : services) {
            var flags = service.getFlags();
            if(flags.isPresent()) {
                return flags;
            }
        }
        return Optional.empty();
    }
}
