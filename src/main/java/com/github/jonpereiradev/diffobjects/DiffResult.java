package com.github.jonpereiradev.diffobjects;


import java.util.Map;


/**
 * Result of a diff with the before and after state.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffResult {

    private final Object expected;
    private final Object current;
    private final boolean equals;
    private Map<String, String> properties;

    public DiffResult(Object expected, Object current, boolean equals) {
        this.expected = expected;
        this.current = current;
        this.equals = equals;
    }

    public Object getExpected() {
        return expected;
    }

    public Object getCurrent() {
        return current;
    }

    public boolean isEquals() {
        return equals;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
