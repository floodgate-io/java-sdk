package io.floodgate.sdk.utils;

import io.floodgate.sdk.User;
import io.floodgate.sdk.models.Rule;
import io.floodgate.sdk.models.RuleComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("RuleHelper")
public class RuleHelperTests {
    @Test
    public void test_equals_false_if_no_values_match() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("42", "43"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("219"));

        assertFalse(result);
    }

    @Test
    public void test_equals_false_if_any_values_match() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("42", "219", "43"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("219"));

        assertTrue(result);
    }

    @Test
    public void test_not_equals_false_if_any_values_match() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.NotEquals;
        rule.setValues(List.of("42", "219", "43"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("219"));

        assertFalse(result);
    }

    @Test
    public void test_not_equals_true_if_no_values_match() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.NotEquals;
        rule.setValues(List.of("42", "43"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("219"));

        assertTrue(result);
    }

    @Test
    public void test_contains_false_if_attr_doesnt_contain_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Contains;
        rule.setValues(List.of("foo", "bar"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertFalse(result);
    }

    @Test
    public void test_contains_true_if_attr_contains_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Contains;
        rule.setValues(List.of("foo", "bar", "baz"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertTrue(result);
    }

    @Test
    public void test_not_contains_false_if_attr_doesnt_contain_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.NotContains;
        rule.setValues(List.of("foo", "bar", "baz"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertFalse(result);
    }

    @Test
    public void test_not_contains_true_if_attr_contains_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.NotContains;
        rule.setValues(List.of("foo", "bar"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertTrue(result);
    }

    @Test
    public void test_ends_with_true_if_attr_ends_with_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.EndsWith;
        rule.setValues(List.of("foo", "qux"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertTrue(result);
    }

    @Test
    public void test_ends_with_false_if_attr_not_ends_with_any_rule_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.EndsWith;
        rule.setValues(List.of("foo", "bar"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("bazqux"));

        assertFalse(result);
    }

    @Test
    public void test_greater_than_true_if_attr_gt_first_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.GreaterThan;
        rule.setValues(List.of("42.1"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("43.1"));

        assertTrue(result);
    }

    @Test
    public void test_greater_than_false_if_attr_lt_first_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.GreaterThan;
        rule.setValues(List.of("42.1"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("41.1"));

        assertFalse(result);
    }

    @Test
    public void test_greater_than_false_if_attr_eq_first_value() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.GreaterThan;
        rule.setValues(List.of("42"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("42"));

        assertFalse(result);
    }

    @Test
    public void test_greater_than_false_if_first_value_garbage() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.GreaterThan;
        rule.setValues(List.of("frog"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("41.1"));

        assertFalse(result);
    }

    @Test
    public void test_greater_than_false_if_attr_garbage() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.GreaterThan;
        rule.setValues(List.of("2"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("moose"));

        assertFalse(result);
    }

    @Test
    public void test_less_than() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.LessThan;
        rule.setValues(List.of("2"));

        var result = RuleHelper.evaluateRuleForUser(rule, new User("3"));

        assertFalse(result);
    }

    @Test
    public void test_custom_attr_positive_case() {
        var rule = new Rule();
        rule.attribute = "foo";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("bar"));

        var user = new User("3");
        user.setAttribute("foo", "bar");
        var result = RuleHelper.evaluateRuleForUser(rule, user);

        assertTrue(result);
    }

    @Test
    public void test_custom_attr_negative_case() {
        var rule = new Rule();
        rule.attribute = "foo";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("bar"));

        var user = new User("3");
        var result = RuleHelper.evaluateRuleForUser(rule, user);

        assertFalse(result);
    }
}
