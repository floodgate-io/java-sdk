package io.floodgate.sdk.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FloodgateClientConfig")
public class FloodgateClientConfigTests {

    @Test
    void test_cannot_instantiate_with_null_api_key() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FloodgateClientConfig(null);
        });
    }

    @Test
    void test_cannot_instantiate_with_empty_api_key() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FloodgateClientConfig("  ");
        });
    }

    @Test
    void test_can_instantiate_if_api_key_present() {
        assertDoesNotThrow(() -> {
            new FloodgateClientConfig("foo-bar-baz");
        });
    }
}
