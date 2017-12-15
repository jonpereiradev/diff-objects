package com.github.jonpereiradev.diffobjects.strategy;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * @author jonpereiradev@gmail.com
 */
public final class DiffMetadata implements Comparable<DiffMetadata> {

    private int order;
    private final String value;
    private final Method method;
    private final DiffStrategy strategy;

    public DiffMetadata(String value, Method method, DiffStrategyType diffStrategyType) {
        this.value = value == null ? StringUtils.EMPTY : value;
        this.method = method;
        this.strategy = diffStrategyType == null ? DiffStrategyType.SINGLE.getStrategy() : diffStrategyType.getStrategy();
    }

    @Override
    public int compareTo(DiffMetadata o) {
        return Integer.compare(order, o.order);
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
}
