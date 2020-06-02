package io.floodgate.sdk.config;


import io.floodgate.sdk.User;

import java.util.Optional;

public class ClientConfig {

    private String apiKey;
    private String localFlagsFilePath;
    private User user;
    private int updateInterval = 60;

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
}
