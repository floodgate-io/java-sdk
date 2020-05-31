package io.floodgate.sdk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.io.FileReader;
import io.floodgate.sdk.models.FeatureFlag;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class FileSystemFeatureFlagService implements FeatureFlagService {

    private final ClientConfig config;
    private final FileReader fileReader;
    private final ObjectMapper json;

    public FileSystemFeatureFlagService(ClientConfig config, FileReader fileReader, ObjectMapper json) {
        this.config = config;
        this.fileReader = fileReader;
        this.json = json;
    }


    public Optional<Map<String,FeatureFlag>> getFlags() {
        var path = config.getLocalFlagsFilePath();

        if(path == null) {
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

            return Optional.of(map);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
