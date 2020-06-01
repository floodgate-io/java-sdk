package io.floodgate.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag {
    public String key;
    public String value;
    public List<Rollout> rollouts;

    public boolean isRollout() {
        return rollouts != null && !rollouts.isEmpty();
    }
}
