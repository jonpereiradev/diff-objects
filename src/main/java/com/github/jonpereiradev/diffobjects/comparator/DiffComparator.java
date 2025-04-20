package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Contract for comparing the equality of two objects of the same type.
 * The class must have a public no-args constructor.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
@FunctionalInterface
public interface DiffComparator<T> {

    /**
     * Checks the equality of two objects.
     *
     * @param expected the object representing the expected state.
     * @param current the object representing the current state.
     *
     * @return {@code true} if the two objects are equal, {@code false} otherwise.
     */
    boolean isEquals(T expected, T current);

}
