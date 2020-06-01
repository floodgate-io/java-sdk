package io.floodgate.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {
    public String attribute;
    public RuleComparator comparator;
    private List<String> values;

    public void setValues(List<String> values) {
        if(values == null || values.isEmpty())
            throw new IllegalArgumentException("Valid rule must have at least one values");
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
