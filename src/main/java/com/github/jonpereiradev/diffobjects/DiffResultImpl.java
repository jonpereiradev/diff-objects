package com.github.jonpereiradev.diffobjects;

import java.util.Map;

/**
 * Default implementation of {@link DiffResult}, representing the outcome of comparing a single field or value.
 * <p>
 * Stores the expected and current values, the equality status, and any additional properties
 * describing the comparison (such as the field name or custom metadata).
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffResultImpl implements DiffResult {

    /**
     * The expected value for the field or comparison.
     */
    private final Object expected;

    /**
     * The current value for the field or comparison.
     */
    private final Object current;

    /**
     * Indicates whether the expected and current values are considered equal.
     */
    private final boolean equals;

    /**
     * Additional properties describing the comparison (e.g., field name, custom metadata).
     */
    private final Map<String, String> properties;

    /**
     * Constructs a new {@code DiffResultImpl} with the given values and properties.
     *
     * @param expected the expected value
     * @param current the current value
     * @param equals {@code true} if the values are considered equal; {@code false} otherwise
     * @param properties additional properties describing the comparison
     */
    DiffResultImpl(Object expected, Object current, boolean equals, Map<String, String> properties) {
        this.expected = expected;
        this.current = current;
        this.equals = equals;
        this.properties = properties;
    }

    /**
     * Returns the name of the field associated with this diff result, if available.
     *
     * @return the field name, or {@code null} if not associated with a specific field
     */
    @Override
    public String getField() {
        return getProperty("field");
    }

    /**
     * Checks if a property with the given name exists in this diff result.
     *
     * @param name the property name
     *
     * @return {@code true} if the property exists; {@code false} otherwise
     */
    @Override
    public boolean containsProperty(String name) {
        return properties.containsKey(name);
    }

    /**
     * Returns the value of the specified property, or {@code null} if not present.
     *
     * @param name the property name
     *
     * @return the property value, or {@code null} if not present
     */
    @Override
    public String getProperty(String name) {
        return properties.get(name);
    }

    /**
     * Returns the value of the specified property, or the given default value if not present.
     *
     * @param name the property name
     * @param defaultValue the value to return if the property is not present
     *
     * @return the property value, or {@code defaultValue} if not present
     */
    @Override
    public String getProperty(String name, String defaultValue) {
        return properties.getOrDefault(name, defaultValue);
    }

    /**
     * Returns the expected value for the field or comparison.
     *
     * @return the expected value
     */
    @Override
    public Object getExpected() {
        return expected;
    }

    /**
     * Returns the current value for the field or comparison.
     *
     * @return the current value
     */
    @Override
    public Object getCurrent() {
        return current;
    }

    /**
     * Indicates whether the expected and current values are considered equal.
     *
     * @return {@code true} if the values are equal; {@code false} otherwise
     */
    @Override
    public boolean isEquals() {
        return equals;
    }

}