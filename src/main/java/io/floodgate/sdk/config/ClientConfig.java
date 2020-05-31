package io.floodgate.sdk.config;


public class ClientConfig {

    private String apiKey;
    private String localFlagsFilePath;

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
}
