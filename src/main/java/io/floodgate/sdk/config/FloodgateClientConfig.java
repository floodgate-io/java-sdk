package io.floodgate.sdk.config;


import io.floodgate.sdk.User;

import java.net.URI;
import java.util.Optional;

/**
 * Configuration object for the floodgate client
 */
public class FloodgateClientConfig {

    private static final int SECONDS = 1000;
    private static final String API_BASE_URL = "https://cdn.floodgate.io";
    private static final String API_VERSION = "v1";

    private String apiKey;
    private String localFlagsFilePath;
    private User user;
    private int apiTimeout = 5 * SECONDS;
    private int autoUpdateInterval = 60 * SECONDS;
    private boolean autoUpdateEnabled = true;

    public FloodgateClientConfig(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("apiKey is required");
        }
        this.apiKey = apiKey;
    }

    /**
     * Gets the API key used for authentication and authorization with Floodgate service
     *
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Gets the path to optional local feature flags file
     *
     * @return path to local feature flags file, or null if not set
     */
    public String getLocalFlagsFilePath() {
        return localFlagsFilePath;
    }

    /**
     * Sets the path to optional local feature flags file
     *
     * @param localFlagsFilePath path to local feature flags file
     */
    public void setLocalFlagsFilePath(String localFlagsFilePath) {
        this.localFlagsFilePath = localFlagsFilePath;
    }

    /**
     * Gets current application user, used for targeting and rollouts.
     *
     * @return Current application user.
     */
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    /**
     * Set current application user, used for targeting and rollouts.
     *
     * @param user Current application user
     */
    public void setUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("user must not be null");
        this.user = user;
    }

    /**
     * Get the SDK version
     *
     * @return Version string for SDK
     */
    public String getVersionString() {
        var descriptor = getClass().getModule().getDescriptor();

        return descriptor != null
                ? descriptor.rawVersion().orElse("Unknown")
                : getClass().getPackage().getImplementationVersion(); // fallback for classpath
    }

    /**
     * Get the timout value in milliseconds used when making http requests to the Floodgate API.
     *
     * <p>
     * Defaults to 5000ms (5 seconds)
     *
     * @return Timeout value for HTTP client.
     */
    public int getApiTimeout() {
        return apiTimeout;
    }

    /**
     * Set the timout value in milliseconds used when making http requests to the Floodgate API.
     *
     * @param apiTimeout value in milliseconds
     */
    public void setApiTimeout(int apiTimeout) {
        this.apiTimeout = apiTimeout;
    }

    /**
     * Get the URL used to load feature flags from Floodgate API.
     */
    public URI getApiUrl() {
        return URI.create(String.format("%s/environment-files/%s/%s/flags-config.json",
                API_BASE_URL,
                getApiKey(),
                API_VERSION));
    }

    /**
     * Gets value representing whether FloodgateClient should automatically reload feature flags on a timer
     *
     * <p>
     * Default value of true
     */
    public boolean isAutoUpdateEnabled() {
        return autoUpdateEnabled;
    }

    /**
     * Set value representing whether FloodgateClient should automatically reload feature flags on a timer
     */
    public void setAutoUpdateEnabled(boolean autoUpdateEnabled) {
        this.autoUpdateEnabled = autoUpdateEnabled;
    }

    /**
     * Gets frequency to reload feature flags in milliseconds
     *
     * <p>
     * Default value of 60,000 milliseconds (1 minute)
     */
    public int getAutoUpdateInterval() {
        return autoUpdateInterval;
    }

    /**
     * Set frequency to reload feature flags in milliseconds
     */
    public void setAutoUpdateInterval(int autoUpdateInterval) {
        this.autoUpdateInterval = autoUpdateInterval;
    }
}
