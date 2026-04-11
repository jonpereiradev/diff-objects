package com.github.jonpereiradev.diffobjects.comparator;

import java.util.Objects;

/**
 * Comparator implementation that checks two objects for equality using their {@code equals} method.
 * <p>
 * This comparator is typically used as the default for property-level equality checks in diff operations.
 * It safely handles {@code null} values and delegates to {@link Objects#equals(Object, Object)}.
 * </p>
 *
 * @param <T> the type of objects to compare
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
public class EqualsComparator<T> implements DiffComparator<T> {

    /**
     * Checks the equality of two objects using {@link Objects#equals(Object, Object)}.
     *
     * @param expected the object with the expected state
     * @param current the object with the current state
     *
     * @return {@code true} if the two objects are equal, {@code false} otherwise
     */
    @Override
    public boolean isEquals(T expected, T current) {
        return Objects.equals(expected, current);
    }

}