module io.floodgate.sdk {
    exports io.floodgate.sdk;
    exports io.floodgate.sdk.config;
    exports io.floodgate.sdk.models to com.fasterxml.jackson.databind;
    exports io.floodgate.sdk.services;

    requires com.fasterxml.jackson.databind;
    requires java.net.http;
}
