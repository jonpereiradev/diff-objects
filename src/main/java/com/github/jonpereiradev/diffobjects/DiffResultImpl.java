package com.github.jonpereiradev.diffobjects;


import java.util.Map;


/**
 * Represents the result of a diff, showing the before and after state.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffResultImpl implements DiffResult {

    private final Object expected;
    private final Object current;
    private final boolean equals;
    private final Map<String, String> properties;

    DiffResultImpl(Object expected, Object current, boolean equals, Map<String, String> properties) {
        this.expected = expected;
        this.current = current;
        this.equals = equals;
        this.properties = properties;
    }

    @Override
    public String getField() {
        return getProperty("field");
    }

    @Override
    public boolean containsProperty(String name) {
        return properties.containsKey(name);
    }

    @Override
    public String getProperty(String name) {
        return properties.get(name);
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return properties.getOrDefault(name, defaultValue);
    }

    @Override
    public Object getExpected() {
        return expected;
    }

    @Override
    public Object getCurrent() {
        return current;
    }

    @Override
    public boolean isEquals() {
        return equals;
    }


}
