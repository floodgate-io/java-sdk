package io.floodgate.sdk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.caching.Constants;
import io.floodgate.sdk.caching.SimpleMemoryCache;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.models.FeatureFlag;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CdnFeatureFlagService implements FeatureFlagService {
    private final ClientConfig config;
    private final ObjectMapper json;
    private final SimpleMemoryCache cache;

    public CdnFeatureFlagService(ClientConfig config, ObjectMapper json, SimpleMemoryCache cache) {
        this.config = config;
        this.json = json;
        this.cache = cache;
    }

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        // NOTE: This isn't async, nor is .Net SDK really

        var client = HttpClient.newHttpClient();

        var etag = cache.get(Constants.API_ETAG);

        var request = HttpRequest.newBuilder(config.getApiUrl())
                .timeout(Duration.ofMillis(config.getApiTimeout()))
                .header("X-FloodGate-SDK-Agent", "java")
                .header("X-FloodGate-SDK-Version", config.getVersionString())
                .header("ETag", etag == null ? "" : etag.toString())
                .build();

        try {
            var stream = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            // TODO: set etag

            var items = this.json.readValue(stream.body(), new TypeReference<List<FeatureFlag>>() {});

            if(items.isEmpty()) {
                return Optional.empty();
            }

            var map = items.stream()
                    .collect(Collectors.toMap(i -> i.key, i -> i));
            return Optional.of(map);
        } catch (IOException e) {
            return Optional.empty();
        } catch (InterruptedException e) {
            return Optional.empty();
        }

    }
}
