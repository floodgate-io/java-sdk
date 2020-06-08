package io.floodgate.sdk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.caching.Constants;
import io.floodgate.sdk.caching.SimpleMemoryCache;
import io.floodgate.sdk.config.FloodGateClientConfig;
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

import static java.lang.System.Logger.Level;

/**
 * Loads feature flags from the FloodGate API
 */
public class CdnFeatureFlagService implements FeatureFlagService {
    private final FloodGateClientConfig config;
    private final ObjectMapper json;
    private final SimpleMemoryCache cache;
    private final HttpClient client;

    private static final System.Logger logger = System.getLogger(CdnFeatureFlagService.class.getName());

    public CdnFeatureFlagService(FloodGateClientConfig config, ObjectMapper json, SimpleMemoryCache cache) {
        this.config = config;
        this.json = json;
        this.cache = cache;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Optional<Map<String, FeatureFlag>> getFlags() {
        // NOTE: This isn't async, nor is .Net SDK really
        // but doesn't matter much as updates are performed on timer thread.

        var apiUrl = config.getApiUrl();
        var eTag = cache.get(Constants.API_ETAG);

        logger.log(Level.DEBUG, "Making request to CDN: {}", apiUrl);
        logger.log(Level.DEBUG, "Current ETag: {}", eTag);

        var request = HttpRequest.newBuilder(config.getApiUrl())
                .timeout(Duration.ofMillis(config.getApiTimeout()))
                .header("X-FloodGate-SDK-Agent", "java")
                .header("X-FloodGate-SDK-Version", config.getVersionString())
                .header("If-None-Match", eTag == null ? "" : eTag.toString())
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if(response.statusCode() == 304) {
                logger.log(Level.DEBUG, "Received HTTP Not Modified");
                return Optional.empty();
            }

            response.headers().firstValue("ETag").ifPresent(e -> cache.set(Constants.API_ETAG, e));

            var items = this.json.readValue(response.body(), new TypeReference<List<FeatureFlag>>() {});

            if(items.isEmpty()) {
                return Optional.empty();
            }

            logger.log(Level.INFO, "Feature flags updated from CDN");
            var map = items.stream().collect(Collectors.toMap(i -> i.key, i -> i));
            return Optional.of(map);
        } catch (IOException e) {
            return Optional.empty();
        } catch (InterruptedException e) {
            return Optional.empty();
        }

    }
}

