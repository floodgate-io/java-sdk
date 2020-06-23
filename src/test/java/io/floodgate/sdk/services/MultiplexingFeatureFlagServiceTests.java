package io.floodgate.sdk.services;

import io.floodgate.sdk.models.FeatureFlag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("MultiplexingFeatureFlagService")
public class MultiplexingFeatureFlagServiceTests {
    @Test
    public void test_returns_empty_optional_if_no_services_provided() {
        var sut = new MultiplexingFeatureFlagService();

        var result = sut.getFlags();
        assertTrue(result.isEmpty());
    }

    @Test
    public void test_doesnt_call_later_if_earlier_returns_items() {
        var a = mock(MultiplexingFeatureFlagService.class);
        var b = mock(MultiplexingFeatureFlagService.class);

        var map = new HashMap<String, List<FeatureFlag>>();
        doReturn(Optional.of(map)).when(a).getFlags();

        var sut = new MultiplexingFeatureFlagService(a, b);

        sut.getFlags();

        verify(a).getFlags();
        verifyNoInteractions(b);
    }

    @Test
    public void test_calls_later_if_earlier_returns_empty() {
        var a = mock(MultiplexingFeatureFlagService.class);
        var b = mock(MultiplexingFeatureFlagService.class);

        var sut = new MultiplexingFeatureFlagService(a, b);

        sut.getFlags();

        verify(a).getFlags();
        verify(b).getFlags();
    }
}
