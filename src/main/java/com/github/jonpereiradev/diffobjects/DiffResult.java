package com.github.jonpereiradev.diffobjects;

import java.util.Map;

/**
 * Result of a diff with the before and after state.
 *
 * @author Jonathan Pereira
 * @since 1.0
 */
public final class DiffResult {

    private final Object before;
    private final Object after;
    private final boolean equals;
    private Map<String, String> properties;

    public DiffResult(Object before, Object after, boolean equals) {
        this.before = before;
        this.after = after;
        this.equals = equals;
    }

    public Object getBefore() {
        return before;
    }

    public Object getAfter() {
        return after;
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
