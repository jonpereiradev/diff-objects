package com.github.jonpereiradev.diffobjects;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

/**
 * Represents the result of comparing a single field or value between two objects.
 * Provides access to the expected and current values, equality status, and additional properties.
 *
 * <p>Instances are typically created via the static factory methods.</p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public interface DiffResult {

    /**
     * Creates a {@code DiffResult} for a value comparison without additional properties.
     *
     * @param expected the expected value
     * @param current the current value
     * @param equals {@code true} if the values are considered equal; {@code false} otherwise
     *
     * @return a new {@code DiffResult} instance
     */
    static DiffResult forValue(Object expected, Object current, boolean equals) {
        return new DiffResultImpl(expected, current, equals, emptyMap());
    }

    /**
     * Creates a {@code DiffResult} for a value comparison with additional properties.
     *
     * @param expected the expected value
     * @param current the current value
     * @param equals {@code true} if the values are considered equal; {@code false} otherwise
     * @param properties a map of additional properties describing the comparison
     *
     * @return a new {@code DiffResult} instance
     */
    static DiffResult forValue(Object expected, Object current, boolean equals, Map<String, String> properties) {
        return new DiffResultImpl(expected, current, equals, unmodifiableMap(properties));
    }

    /**
     * Returns the name of the field associated with this diff result, if applicable.
     *
     * @return the field name, or {@code null} if not associated with a specific field
     */
    String getField();

    /**
     * Returns the expected value for the field or comparison.
     *
     * @return the expected value
     */
    Object getExpected();

    /**
     * Returns the current value for the field or comparison.
     *
     * @return the current value
     */
    Object getCurrent();

    /**
     * Indicates whether the expected and current values are considered equal.
     *
     * @return {@code true} if the values are equal; {@code false} otherwise
     */
    boolean isEquals();

    /**
     * Checks if a property with the given name exists in this diff result.
     *
     * @param name the property name
     *
     * @return {@code true} if the property exists; {@code false} otherwise
     */
    boolean containsProperty(String name);

    /**
     * Returns the value of the specified property, or {@code null} if not present.
     *
     * @param name the property name
     *
     * @return the property value, or {@code null} if not present
     */
    String getProperty(String name);

    /**
     * Returns the value of the specified property, or the given default value if not present.
     *
     * @param name the property name
     * @param defaultValue the value to return if the property is not present
     *
     * @return the property value, or {@code defaultValue} if not present
     */
    String getProperty(String name, String defaultValue);

}