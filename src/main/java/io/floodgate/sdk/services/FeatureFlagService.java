package io.floodgate.sdk.services;

import io.floodgate.sdk.models.FeatureFlag;

import java.util.Map;
import java.util.Optional;

public interface FeatureFlagService {
    Optional<Map<String,FeatureFlag>> getFlags();

    default void reload() {
        // NOOP
    }
}
