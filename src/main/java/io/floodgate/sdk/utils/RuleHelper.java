package io.floodgate.sdk.utils;

import io.floodgate.sdk.User;
import io.floodgate.sdk.models.Rule;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class RuleHelper {
    public static boolean evaluateRuleForUser(Rule rule, User user) {
        var opt = user.getAttribute(rule.attribute);
        if(opt.isEmpty())
            return false;

        var attribute = opt.get().toLowerCase();

        switch (rule.comparator) {
            case Equals:
                return rule.getValues().stream().anyMatch(v -> v.equalsIgnoreCase(attribute));
            case NotEquals:
                return !rule.getValues().stream().anyMatch(v -> v.equalsIgnoreCase(attribute));
            case Contains:
                return rule.getValues().stream().map(r -> r.toLowerCase()).anyMatch(v -> attribute.contains(v));
            case NotContains:
                return !rule.getValues().stream().map(r -> r.toLowerCase()).anyMatch(v -> attribute.contains(v));
            case EndsWith:
                return rule.getValues().stream().map(r -> r.toLowerCase()).anyMatch(v ->  attribute.endsWith(v));
            case GreaterThan:
                return evaluateGreaterThan(rule.getValues(), attribute, false);
            case GreaterThanOrEqual:
                return evaluateGreaterThan(rule.getValues(), attribute, true);
            case LessThan:
                return evaluateLessThan(rule.getValues(), attribute, false);
            case LessThanOrEquals:
                return evaluateLessThan(rule.getValues(), attribute, true);
            case Invalid:
            default:
                return false;
        }
    }

    private static boolean evaluateGreaterThan(List<String> values, String attribute, boolean allowEquals) {
        // TODO: This only checks first value, .Net does same, is this OK?
        // TODO: Inconsistent with other rules, not documented on site.

        var opt = values.stream().findFirst();

        try {
            var rule = Double.valueOf(opt.get());
            var value = Double.valueOf(attribute);

            // Normally one should validate floating point equality
            // to a degree of precision
            // but all our values are from Strings none from expressions that
            // could introduce rounding errors so not an issue.
            return allowEquals ? value >= rule : value > rule;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean evaluateLessThan(List<String> values, String attribute, boolean allowEquals) {
        var opt = values.stream().findFirst();

        try {
            var rule = Double.valueOf(opt.get());
            var value = Double.valueOf(attribute);

            // Normally one should validate floating point equality
            // to a degree of precision
            // but all our values are from Strings none from expressions that
            // could introduce rounding errors so not an issue.
            return allowEquals ? value <= rule : value < rule;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
}
