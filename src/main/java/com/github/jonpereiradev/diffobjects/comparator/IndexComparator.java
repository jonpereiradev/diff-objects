package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Check two objects for equality using the equals method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.1
 */
public class IndexComparator<T> implements DiffComparator<T> {

    /**
     * Check the equality of two objects.
     *
     * @param o1 first instance to compare the equality.
     * @param o2 second instance to compare the equality.
     *
     * @return {@code true} if the two objects are equals.
     */
    @Override
    public boolean equals(T o1, T o2) {
        throw new UnsupportedOperationException();
    }
}
