package io.floodgate.sdk;

import io.floodgate.sdk.config.FloodgateClientConfig;
import io.floodgate.sdk.models.FeatureFlag;
import io.floodgate.sdk.stubs.StubFeatureFlagService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultFloodgateClientTests {

    private static FloodgateClientConfig simpleConfig = new FloodgateClientConfig("foo");

    @Test
    public void test_get_string_value_returns_default_if_not_found() {
        var service = new StubFeatureFlagService();
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key");

        assertEquals("false", result);
    }

    @Test
    public void test_get_string_value_returns_specified_default_if_not_found() {
        var service = new StubFeatureFlagService();
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", "foo");

        assertEquals("foo", result);
    }

    @Test
    public void test_get_string_value_returns_value_if_present() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "bar"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", "foo");

        assertEquals("bar", result);
    }

    @Test
    public void test_get_int_value_returns_default_if_not_found() {
        var service = new StubFeatureFlagService();
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 42);

        assertEquals(42, result);
    }

    @Test
    public void test_get_int_value_returns_default_on_parse_error() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "not-a-number"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 42);

        assertEquals(42, result);
    }

    @Test
    public void test_get_int_value_returns_parsed_value() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "219"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 42);

        assertEquals(219, result);
    }

    @Test
    public void test_get_bool_value_returns_default_if_not_found() {
        var service = new StubFeatureFlagService();
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", true);

        assertTrue(result);
    }

    @Test
    public void test_get_bool_value_returns_parsed_value() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "false"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", true);

        assertFalse(result);
    }


    @Test
    public void test_get_double_value_returns_default_if_not_found() {
        var service = new StubFeatureFlagService();
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 1.23);

        assertEquals(1.23, result);
    }

    @Test
    public void test_get_double_value_returns_default_on_parse_error() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "not-a-number"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 1.23);

        assertEquals(1.23, result);
    }

    @Test
    public void test_get_double_value_returns_parsed_value() {
        var service = new StubFeatureFlagService();
        service.add(new FeatureFlag("a-key", "2.34"));
        var sut = new DefaultFloodgateClient(simpleConfig, service);

        var result = sut.getValue("a-key", 1.23);

        assertEquals(2.34, result);
    }
}
