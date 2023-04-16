package com.github.jonpereiradev.diffobjects;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

public interface DiffResult {

    static DiffResult forValue(Object expected, Object current, boolean equals) {
        return new DiffResultImpl(expected, current, equals, emptyMap());
    }

    static DiffResult forValue(Object expected, Object current, boolean equals, Map<String, String> properties) {
        return new DiffResultImpl(expected, current, equals, unmodifiableMap(properties));
    }

    Object getExpected();

    Object getCurrent();

    boolean isEquals();

    Map<String, String> getProperties();
}
