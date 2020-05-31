package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.models.FeatureFlag;

import java.util.Map;
import java.util.Optional;

public class CdnFeatureFlagService implements FeatureFlagService{
    private final ClientConfig config;
    private final ObjectMapper json;

    public CdnFeatureFlagService(ClientConfig config, ObjectMapper json)  {
        this.config = config;
        this.json = json;
    }

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        // TODO: Implement this one
        return Optional.empty();
    }
}
