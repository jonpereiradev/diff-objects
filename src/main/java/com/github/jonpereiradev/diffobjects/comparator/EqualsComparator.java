package com.github.jonpereiradev.diffobjects.comparator;

import java.util.Objects;

/**
 * Checks two objects for equality using their {@code equals} method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
public class EqualsComparator<T> implements DiffComparator<T> {

    /**
     * Checks the equality of two objects.
     *
     * @param expected the object with the expected state.
     * @param current the object with the current state.
     *
     * @return {@code true} if the two objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean isEquals(T expected, T current) {
        return Objects.equals(expected, current);
    }

}
