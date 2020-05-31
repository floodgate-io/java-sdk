package io.floodgate.sdk;

public interface FloodGateClient {
    /**
     * Get value of flag for the given key
     *
     * @param key The flag key to check
     * @return String value of the flag. If no flag data is found for the key given the default string of `False` is returned
     */
    default String getValue(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key  The flag key to check
     * @param user A user to compare against when flag targeting is enabled
     * @return String value of the flag. If no flag data is found for the key given the default string of `False` is returned
     */
    default String getValue(String key, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A default value to return if the no flag data is found for the key given
     * @return String value of the flag or the defaultValue if not found
     */
    default String getValue(String key, String defaultValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @param user         A user to compare against when flag targeting is enabled
     * @return String value of the flag or the defaultValue if not found
     */
    default String getValue(String key, String defaultValue, User user) {
        throw new UnsupportedOperationException();
    }


    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @return Boolean value of the flag or the defaultValue if not found
     */
    default boolean getValue(String key, boolean defaultValue) {
        return getValue(key, defaultValue, null);
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @param user         A user to compare against when flag targeting is enabled
     * @return Boolean value of the flag or the defaultValue if not found
     */
    boolean getValue(String key, boolean defaultValue, User user);

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @return Integer value of the flag or the defaultValue if not found
     */
    default int getValue(String key, int defaultValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @param user         A user to compare against when flag targeting is enabled
     * @return Integer value of the flag or the defaultValue if not found
     */
    default int getValue(String key, int defaultValue, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @return Double value of the flag or the defaultValue if not found
     */
    default double getValue(String key, double defaultValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get value of flag for the given key
     *
     * @param key          The flag key to check
     * @param defaultValue A fallback value to return if the no flag data is found for the key given
     * @param user         A user to compare against when flag targeting is enabled
     * @return Dobule value of the flag or the defaultValue if not found
     */
    default double getValue(String key, double defaultValue, User user) {
        throw new UnsupportedOperationException();
    }


    /**
     * Flush any events in the event queue to the Floodgate servers
     */
    void flushEvents();
}
