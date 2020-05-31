package io.floodgate.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.floodgate.sdk.config.ClientConfig;
import io.floodgate.sdk.io.FileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("FileSystemFeatureFlagsService")
public class FileSystemFeatureFlagServiceTests {
    @Test
    public void test_returns_optional_empty_if_local_path_not_set() {
        var mapper = new ObjectMapper();
        var fileReader = new FileReader();
        var config = new ClientConfig("a");
        config.setLocalFlagsFilePath(UUID.randomUUID().toString());

        var sut = new FileSystemFeatureFlagService(config, fileReader, mapper);

        var result = sut.getFlags();

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_returns_optional_empty_for_invalid_path() {
        var mapper = new ObjectMapper();
        var fileReader = new FileReader();
        var config = new ClientConfig("a");
        config.setLocalFlagsFilePath(UUID.randomUUID().toString());

        var sut = new FileSystemFeatureFlagService(config, fileReader, mapper);

        var result = sut.getFlags();

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_returns_optional_empty_when_invalid_file_contents() throws FileNotFoundException {
        var mapper = new ObjectMapper();
        var config = new ClientConfig("a");
        config.setLocalFlagsFilePath(UUID.randomUUID().toString());

        var fileReader = mock(FileReader.class);

        var stream = new ByteArrayInputStream("not-valid-json".getBytes());
        doReturn(stream).when(fileReader).getStream(anyString());

        var sut = new FileSystemFeatureFlagService(config, fileReader, mapper);

        var result = sut.getFlags();

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_returns_optional_empty_when_given_empty_json_array() throws FileNotFoundException {
        var mapper = new ObjectMapper();
        var config = new ClientConfig("a");
        config.setLocalFlagsFilePath(UUID.randomUUID().toString());

        var fileReader = mock(FileReader.class);

        var stream = new ByteArrayInputStream("[]".getBytes());
        doReturn(stream).when(fileReader).getStream(anyString());

        var sut = new FileSystemFeatureFlagService(config, fileReader, mapper);

        var result = sut.getFlags();

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_returns_list_when_given_valid_json() throws FileNotFoundException {
        var mapper = new ObjectMapper();
        var config = new ClientConfig("a");
        config.setLocalFlagsFilePath(UUID.randomUUID().toString());

        var fileReader = mock(FileReader.class);

        String json = "[{\"key\": \"simple\",\"value\": \"42\",\"is_rollout\": false,\"is_targeting_enabled\": false}]";

        var stream = new ByteArrayInputStream(json.getBytes());
        doReturn(stream).when(fileReader).getStream(anyString());

        var sut = new FileSystemFeatureFlagService(config, fileReader, mapper);

        var result = sut.getFlags();

        assertTrue(result.isPresent());

        var featureFlag = result.get().get("simple");
        assertEquals("42", featureFlag.value);
    }
}

