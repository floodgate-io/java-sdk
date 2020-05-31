package io.floodgate.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag {
    public String id;
    public String key;
    public String value;
}
