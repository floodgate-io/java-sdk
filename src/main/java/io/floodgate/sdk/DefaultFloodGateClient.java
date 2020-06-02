package io.floodgate.sdk;

import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.models.FeatureFlag;
import io.floodgate.sdk.services.FeatureFlagService;
import io.floodgate.sdk.utils.RolloutHelper;
import io.floodgate.sdk.utils.TargetHelper;

import java.util.Optional;
import java.util.stream.Stream;

class DefaultFloodGateClient implements FloodGateClient {
    private final ClientConfig config;
    private final FeatureFlagService flagService;

    public DefaultFloodGateClient(ClientConfig config, FeatureFlagService flagService) {
        this.config = config;
        this.flagService = flagService;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue(String key, String defaultValue, Optional<User> overrideUser) {
        var opt = getFlagValue(key, overrideUser);

        return opt.orElse(defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getValue(String key, boolean defaultValue, Optional<User> overrideUser) {

        var opt = getFlagValue(key, overrideUser);

        return opt.map(o -> Boolean.valueOf(o))
                .orElse(defaultValue);
    }

    private Optional<String> getFlagValue(String key, Optional<User> overrideUser) {

        // TODO: user targeting from config user
        var user = overrideUser.or(() -> config.getUser());

        var opt = flagService.getFlags();

        if (opt.isEmpty())
            return Optional.empty();

        var flags = opt.get();

        var flag = flags.get(key);
        if (flag == null)
            return Optional.empty();

        return Stream.of(
                getTargetedValue(flag, user),
                getRolloutValue(flag, user),
                Optional.of(flag.value)
        ).filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<String> getRolloutValue(FeatureFlag flag, Optional<User> user) {
        if (!flag.isRollout() || user.isEmpty())
            return Optional.empty();

        var scale = RolloutHelper.GetScale(flag, user.get());

        var lower = 0;
        var upper = 0;

        for (var rollout : flag.rollouts) {
            upper += rollout.percentage;

            if (scale >= lower && scale <= upper) {
                return Optional.ofNullable(rollout.value);
            }

            lower += rollout.percentage;
        }

        return Optional.empty();
    }

    private Optional<String> getTargetedValue(FeatureFlag flag, Optional<User> user) {
        if(!flag.isTargeted() || user.isEmpty())
            return Optional.empty();

        var targeted = flag.targets.stream()
                .filter(t -> TargetHelper.isValidTargetForUser(t, user.get()))
                .findFirst();

        return targeted.map(t -> t.value);
    }
}
