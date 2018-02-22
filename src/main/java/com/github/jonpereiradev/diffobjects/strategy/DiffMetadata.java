package com.github.jonpereiradev.diffobjects.strategy;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Metadata with the information of a field/method to compare on diff execution.
 *
 * @author jonpereiradev@gmail.com
 */
public final class DiffMetadata implements Comparable<DiffMetadata> {

    private static final DiffStrategy DEFAULT_STRATEGY = DiffStrategyType.SINGLE.getStrategy();

    private int order;
    private final String value;
    private final Method method;
    private final DiffStrategy strategy;
    private final Map<String, String> properties;

    public DiffMetadata(String value, Method method, DiffStrategyType diffStrategyType) {
        this.value = StringUtils.trimToEmpty(value);
        this.method = method;
        this.strategy = diffStrategyType == null ? DEFAULT_STRATEGY : diffStrategyType.getStrategy();
        this.properties = new HashMap<>();
    }

    @Override
    public int compareTo(DiffMetadata o) {
        return Integer.compare(order, o.order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiffMetadata that = (DiffMetadata) o;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public Method getMethod() {
        return method;
    }

    public DiffStrategy getStrategy() {
        return strategy;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
