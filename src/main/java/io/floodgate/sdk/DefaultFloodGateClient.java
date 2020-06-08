package io.floodgate.sdk;

import io.floodgate.sdk.config.FloodGateClientConfig;
import io.floodgate.sdk.models.FeatureFlag;
import io.floodgate.sdk.services.FeatureFlagService;
import io.floodgate.sdk.utils.RolloutHelper;
import io.floodgate.sdk.utils.TargetHelper;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level;

class DefaultFloodGateClient implements FloodGateClient {
    private final FloodGateClientConfig config;
    private final FeatureFlagService flagService;
    private final Timer timer;

    private static final System.Logger logger = System.getLogger(DefaultFloodGateClient.class.getName());

    public DefaultFloodGateClient(FloodGateClientConfig config, FeatureFlagService flagService) {
        this.config = config;
        this.flagService = flagService;
        this.timer = new Timer(true);

        logger.log(Level.DEBUG, "Auto Update enabled: {}", config.isAutoUpdateEnabled());
        if(config.isAutoUpdateEnabled()) {
            this.timer.schedule(new AutoUpdateTask(), 0, config.getAutoUpdateInterval());
        }
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

    @Override
    public int getValue(String key, int defaultValue, Optional<User> overrideUser) {
        var opt = getFlagValue(key, overrideUser);

        try{
            return opt.map(o -> Integer.valueOf(o))
                    .orElse(defaultValue);
        }
        catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    @Override
    public double getValue(String key, double defaultValue, Optional<User> overrideUser) {
        var opt = getFlagValue(key, overrideUser);

        try{
            return opt.map(o -> Double.valueOf(o))
                    .orElse(defaultValue);
        }
        catch (NumberFormatException ex){
            return defaultValue;
        }
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
        if (!flag.isTargeted() || user.isEmpty())
            return Optional.empty();

        var targeted = flag.targets.stream()
                .filter(t -> TargetHelper.isValidTargetForUser(t, user.get()))
                .findFirst();

        return targeted.map(t -> t.value);
    }


    private class AutoUpdateTask extends TimerTask {
        @Override
        public void run() {
            logger.log(Level.DEBUG, "Begin auto update of cached flags");
            flagService.reload();
        }
    }
}
