package io.floodgate.sdk;

import java.util.HashMap;
import java.util.Optional;


/**
 * Object to represent application user in your application.
 * <p>
 * Useful for releasing features to only a subset of all users.
 */
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

    /**
     * Used internally when evaluating rollouts and targeted features.
     * @param name attribute name
     * @return value for given attribute
     */
    public Optional<String> getAttribute(String name) {
        return Optional.ofNullable(attributes.get(name.toLowerCase()));
    }


    /**
     * Allows you to associate attributes with a user.
     *
     * <p>
     * Replaces current value of attribute if already set.
     *
     * <p>
     * Examples:
     *
     * <ul>
     *     <li>Email</li>
     *     <li>Country</li>
     *     <li>Department</li>
     * </ul>
     * @param name attribute name
     * @param value value of attribute
     */
    public void setAttribute(String name, String value) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name is required");

        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException("value is required");

        attributes.put(name.toLowerCase(), value);
    }
}
