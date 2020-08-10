package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Contract to compare the equality from two objects of same type. Must have a public no args constructor.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
@FunctionalInterface
public interface DiffComparator<T> {

    /**
     * Check the equality of two objects.
     *
     * @param expected the object with the expected state.
     * @param current the object with the current state.
     *
     * @return {@code true} if the two objects are equals.
     */
    boolean equals(T expected, T current);

}
