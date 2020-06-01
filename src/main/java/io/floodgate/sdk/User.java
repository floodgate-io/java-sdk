package io.floodgate.sdk;

public class User {
    private String id;

    public User(String id) {
        if(id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id is required");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
