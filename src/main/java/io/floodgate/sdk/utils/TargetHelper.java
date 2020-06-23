package io.floodgate.sdk.utils;

import io.floodgate.sdk.User;
import io.floodgate.sdk.models.Target;

import static io.floodgate.sdk.utils.RuleHelper.evaluateRuleForUser;

public class TargetHelper {
    public static boolean isValidTargetForUser(Target target, User user) {
        return target.getRules().stream()
                .allMatch(r -> evaluateRuleForUser(r, user));
    }
}
