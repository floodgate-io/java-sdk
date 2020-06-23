package io.floodgate.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Target {
    public String value;
    private List<Rule> rules;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        if(rules == null || rules.isEmpty())
            throw new IllegalArgumentException("Valid target must have at least one rule");
        this.rules = rules;
    }
}
