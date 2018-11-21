package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Contract to compare the equality from two objects of same type. Must have a public no args constructor.
 *
 * @author Jonathan Pereira
 * @since 1.1
 */
@FunctionalInterface
public interface DiffComparator<T> {

    /**
     * Check the equality of two objects.
     *
     * @param o1 first instance to compare the equality.
     * @param o2 second instance to compare the equality.
     *
     * @return {@code true} if the two objects are equals.
     */
    boolean equals(T o1, T o2);

}
