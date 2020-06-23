package io.floodgate.sdk.utils;

import io.floodgate.sdk.User;
import io.floodgate.sdk.models.Rule;
import io.floodgate.sdk.models.RuleComparator;
import io.floodgate.sdk.models.Target;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TargetHelper")
public class TargetHelperTests {

    @Test
    public void test_isValidTargetForUser_false_if_single_rule_evaluates_false() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("42"));

        var target = new Target();
        target.value = "foo";
        target.setRules(List.of(rule));

        var result = TargetHelper.isValidTargetForUser(target, new User("219"));

        assertFalse(result);
    }

    @Test
    public void test_isValidTargetForUser_false_if_any_rule_evaluates_false() {
        var ruleOne = new Rule();
        ruleOne.attribute = "id";
        ruleOne.comparator = RuleComparator.Equals;
        ruleOne.setValues(List.of("42"));

        var ruleTwo = new Rule();
        ruleTwo.attribute = "id";
        ruleTwo.comparator = RuleComparator.Equals;
        ruleTwo.setValues(List.of("219"));

        var target = new Target();
        target.value = "foo";
        target.setRules(List.of(ruleOne, ruleTwo));

        var result = TargetHelper.isValidTargetForUser(target, new User("42"));

        assertFalse(result);
    }

    @Test
    public void test_isValidTargetForUser_false_if_all_rules_held_evaluate_true() {
        var rule = new Rule();
        rule.attribute = "id";
        rule.comparator = RuleComparator.Equals;
        rule.setValues(List.of("42"));

        var target = new Target();
        target.value = "foo";
        target.setRules(List.of(rule));

        var result = TargetHelper.isValidTargetForUser(target, new User("42"));

        assertTrue(result);
    }
}
