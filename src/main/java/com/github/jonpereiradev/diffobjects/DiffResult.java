package com.github.jonpereiradev.diffobjects;

import java.util.Map;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResult<T> {

    private T before;
    private T after;
    private boolean equals;
    private Map<String, String> properties;

    public DiffResult(T before, T after, boolean equals) {
        this.before = before;
        this.after = after;
        this.equals = equals;
    }

    public T getBefore() {
        return before;
    }

    public T getAfter() {
        return after;
    }

    public boolean isEquals() {
        return equals;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
