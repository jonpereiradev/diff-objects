package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Functional interface representing a contract for comparing the equality of two objects of the same type.
 * <p>
 * Implementations must provide a public no-args constructor.
 * This interface is typically used to define custom logic for determining whether two objects
 * are considered equal within the context of a diff operation.
 * </p>
 *
 * @param <T> the type of objects to compare
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
@FunctionalInterface
public interface DiffComparator<T> {

    /**
     * Checks the equality of two objects.
     *
     * @param expected the object representing the expected state
     * @param current the object representing the current state
     *
     * @return {@code true} if the two objects are considered equal; {@code false} otherwise
     */
    boolean isEquals(T expected, T current);

}