package io.floodgate.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag {
    public String key;
    public String value;
    public List<Rollout> rollouts;
    public List<Target> targets;

    public boolean isRollout() {
        return rollouts != null && !rollouts.isEmpty();
    }

    public boolean isTargeted() {
        return targets != null && !targets.isEmpty();
    }
}
