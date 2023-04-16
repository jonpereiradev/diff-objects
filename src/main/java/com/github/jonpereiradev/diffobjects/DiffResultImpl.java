package com.github.jonpereiradev.diffobjects;


import java.util.Map;


/**
 * Result of a diff with the before and after state.
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

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

}
