package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Metadata containing information about a field or method to be compared during the diff execution.
 * <p>
 * Stores the property path, reflection method, diff strategy, comparator, and additional properties
 * for a single field or method involved in a diff operation. Also supports ordering for evaluation.
 * </p>
 *
 * <p>
 * Instances of this class are used internally to drive the comparison logic for each mapped property.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public final class DiffMetadata implements Comparable<DiffMetadata> {

    /**
     * The default diff strategy used if none is specified.
     */
    private static final DiffStrategy DEFAULT_STRATEGY = DiffStrategyType.SINGLE.getStrategy();

    /**
     * The property path or value to be compared (may be nested, e.g., "address.city").
     */
    private final String value;

    /**
     * The reflection method used to access the property value.
     */
    private final Method method;

    /**
     * The diff strategy to use for this property (single, nested, or collection).
     */
    private final DiffStrategy strategy;

    /**
     * The comparator used to determine equality for this property.
     */
    private final DiffComparator<?> comparator;

    /**
     * Additional properties or metadata associated with this mapping.
     */
    private final Map<String, String> properties;

    /**
     * The evaluation order for this property (used if {@link com.github.jonpereiradev.diffobjects.annotation.DiffOrder} is present).
     */
    private int order;

    /**
     * Constructs a new {@code DiffMetadata} instance with the given configuration.
     *
     * @param value the property path or value to be compared
     * @param method the reflection method to access the property
     * @param diffStrategyType the diff strategy type (single, nested, collection)
     * @param comparator the comparator to use for equality checks
     */
    public DiffMetadata(
        String value,
        Method method,
        DiffStrategyType diffStrategyType,
        DiffComparator<?> comparator) {
        this.value = value == null ? "" : value.trim();
        this.method = Objects.requireNonNull(method);
        this.strategy = diffStrategyType == null ? DEFAULT_STRATEGY : diffStrategyType.getStrategy();
        this.comparator = Objects.requireNonNull(comparator);
        this.properties = new HashMap<>();
    }

    /**
     * Compares this metadata to another by their evaluation order.
     *
     * @param o the other {@code DiffMetadata} to compare to
     *
     * @return a negative integer, zero, or a positive integer as this object's order is less than, equal to, or greater than the specified object's order
     */
    @Override
    public int compareTo(DiffMetadata o) {
        return Integer.compare(order, o.order);
    }

    /**
     * Checks equality based on the underlying reflection method.
     *
     * @param o the object to compare to
     *
     * @return {@code true} if the methods are equal; {@code false} otherwise
     */
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

    /**
     * Returns the hash code based on the underlying method.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(method);
    }

    /**
     * Sets the evaluation order for this metadata.
     *
     * @param order the order value
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Returns the property path or value to be compared.
     *
     * @return the property path or value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the reflection method used to access the property.
     *
     * @return the reflection method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Returns the diff strategy for this property.
     *
     * @return the diff strategy
     */
    public DiffStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns the comparator used for equality checks.
     *
     * @return the comparator
     */
    public DiffComparator<?> getComparator() {
        return comparator;
    }

    /**
     * Returns the additional properties or metadata for this mapping.
     *
     * @return the properties map
     */
    public Map<String, String> getProperties() {
        return properties;
    }
}