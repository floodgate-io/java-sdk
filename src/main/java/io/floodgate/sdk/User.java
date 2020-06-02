package io.floodgate.sdk;

import java.util.HashMap;
import java.util.Optional;

public class User {
    private HashMap<String, String> attributes;

    public User(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id is required");
        }
        attributes = new HashMap<>();
        attributes.put("id", id);
    }

    public String getId() {
        return attributes.get("id");
    }

    public Optional<String> getAttribute(String name) {
        return Optional.ofNullable(attributes.get(name.toLowerCase()));
    }

    public void setAttribute(String name, String value) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name is required");

        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException("value is required");

        attributes.put(name.toLowerCase(), value);
    }
}
