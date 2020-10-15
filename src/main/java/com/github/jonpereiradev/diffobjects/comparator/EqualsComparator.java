package com.github.jonpereiradev.diffobjects.comparator;

import java.util.Objects;

/**
 * Check two objects for equality using the equals method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
public class EqualsComparator<T> implements DiffComparator<T> {

    /**
     * Check the equality of two objects.
     *
     * @param expected the object with the expected state.
     * @param current the object with the current state.
     *
     * @return {@code true} if the two objects are equals.
     */
    @Override
    public boolean isEquals(T expected, T current) {
        return Objects.equals(expected, current);
    }

}
