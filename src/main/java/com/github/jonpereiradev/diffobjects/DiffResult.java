package com.github.jonpereiradev.diffobjects;

import java.util.Map;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffResult {

    private Object before;
    private Object after;
    private boolean equals;
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

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
