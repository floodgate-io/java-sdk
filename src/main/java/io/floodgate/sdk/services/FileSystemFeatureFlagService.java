package io.floodgate.sdk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.config.FloodGateClientConfig;
import io.floodgate.sdk.io.FileReader;
import io.floodgate.sdk.models.FeatureFlag;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.System.Logger.Level;

/**
 * Loads feature flags from a file on the local file system.
 */
class FileSystemFeatureFlagService implements FeatureFlagService {

    private final FloodGateClientConfig config;
    private final FileReader fileReader;
    private final ObjectMapper json;

    private static final System.Logger logger = System.getLogger(FileSystemFeatureFlagService.class.getName());

    public FileSystemFeatureFlagService(FloodGateClientConfig config, FileReader fileReader, ObjectMapper json) {
        this.config = config;
        this.fileReader = fileReader;
        this.json = json;
    }


    public Optional<Map<String,FeatureFlag>> getFlags() {
        var path = config.getLocalFlagsFilePath();

        if(path == null) {
            logger.log(Level.DEBUG, "Local feature flags file path has not been configured");
            return Optional.empty();
        }

        try {
            var stream = fileReader.getStream(path);

            var items = this.json.readValue(stream, new TypeReference<List<FeatureFlag>>() {});
            if(items.isEmpty()) {
                return Optional.empty();
            }

            var map = items.stream()
                    .collect(Collectors.toMap(i -> i.key, i -> i));

            logger.log(Level.DEBUG, "Loaded flags from local feature flags file");

            return Optional.of(map);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
