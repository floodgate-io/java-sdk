package io.floodgate.sdk.config;


import io.floodgate.sdk.User;

import java.net.URI;
import java.util.Optional;

public class ClientConfig {

    private static final int SECONDS = 1000;
    private static final String API_BASE_URL = "https://cdn.floodgate.io";
    private static final String API_VERSION = "v1";

    private String apiKey;
    private String localFlagsFilePath;
    private User user;
    private int updateInterval = 60 * SECONDS;
    private int apiTimeout = 5 * SECONDS;

    public ClientConfig(String apiKey) {
        if(apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("apiKey is required");
        }
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getLocalFlagsFilePath() {
        return localFlagsFilePath;
    }

    public void setLocalFlagsFilePath(String localFlagsFilePath) {
        this.localFlagsFilePath = localFlagsFilePath;
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public void setUser(User user) {
        if(user == null)
            throw new IllegalArgumentException("user must not be null");
        this.user = user;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public String getVersionString() {
        return getClass().getPackage().getImplementationVersion();
    }

    public int getApiTimeout() {
        return apiTimeout;
    }

    public void setApiTimeout(int apiTimeout) {
        this.apiTimeout = apiTimeout;
    }

    public URI getApiUrl() {
        return URI.create(String.format("%s/environment-files/%s/%s/flags-config.json",
                API_BASE_URL,
                getApiKey(),
                API_VERSION));
    }
}
