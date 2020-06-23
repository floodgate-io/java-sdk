package io.floodgate.sdk.stubs;

import io.floodgate.sdk.models.FeatureFlag;
import io.floodgate.sdk.services.FeatureFlagService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StubFeatureFlagService implements FeatureFlagService {
    private Map<String, FeatureFlag> flags = new HashMap<>();

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        return Optional.ofNullable(flags);
    }

    public void add(FeatureFlag flag) {
        flags.put(flag.key, flag);
    }
}
