package io.floodgate.sdk.services;

import io.floodgate.sdk.models.FeatureFlag;

import java.util.Map;
import java.util.Optional;

public class CdnFeatureFlagService implements FeatureFlagService{
    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        // TODO: Implement this one
        return Optional.empty();
    }
}
