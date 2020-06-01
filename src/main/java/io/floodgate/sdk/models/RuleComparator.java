package io.floodgate.sdk.models;

public enum RuleComparator {
    Invalid, // Ordinal 0, values from server start at 1
    Equals,
    NotEquals,
    GreaterThan,
    GreaterThanOrEqual,
    LessThan,
    LessThanOrEquals,
    Contains,
    NotContains,
    EndsWith,
}
