package com.github.jonpereiradev.diff.objects;

import java.lang.reflect.Method;

/**
 * @author jonpereiradev@gmail.com
 */
final class DiffMetadata implements Comparable<DiffMetadata> {

    private DiffProperty annotation;
    private DiffStrategy strategy;
    private DiffOrder order;
    private Method method;

    @Override
    public int compareTo(DiffMetadata o) {
        Integer thisOrder = getOrder() == null ? Integer.MAX_VALUE : getOrder().value();
        Integer otherOrder = o.getOrder() == null ? Integer.MAX_VALUE : o.getOrder().value();

        return thisOrder.compareTo(otherOrder);
    }

    public DiffProperty getAnnotation() {
        return annotation;
    }

    public void setAnnotation(DiffProperty annotation) {
        this.annotation = annotation;
    }

    public DiffStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DiffStrategy strategy) {
        this.strategy = strategy;
    }

    public DiffOrder getOrder() {
        return order;
    }

    public void setOrder(DiffOrder order) {
        this.order = order;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
