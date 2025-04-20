package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Check two objects for equality using the equals method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
public class IndexComparator<T> implements DiffComparator<T> {

    /**
     * Check the equality of two objects.
     *
     * @param expected first instance to compare the equality.
     * @param current second instance to compare the equality.
     *
     * @return {@code true} if the two objects are equals.
     */
    @Override
    public boolean isEquals(T expected, T current) {
        throw new UnsupportedOperationException();
    }

}
