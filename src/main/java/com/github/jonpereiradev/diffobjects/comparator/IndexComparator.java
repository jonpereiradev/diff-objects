package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Comparator implementation intended to check two objects for equality using their {@code equals} method.
 * <p>
 * This class is a placeholder for index-based comparison logic in diff operations.
 * The {@link #isEquals(Object, Object)} method is not implemented and will throw
 * {@link UnsupportedOperationException} if called.
 * </p>
 *
 * @param <T> the type of objects to compare
 *
 * @author Jonathan Pereira
 * @since 1.1.0
 */
public class IndexComparator<T> implements DiffComparator<T> {

    /**
     * Constructs a new {@code IndexComparator}.
     */
    public IndexComparator() {
    }

    /**
     * Checks the equality of two objects.
     * <p>
     * This method is not implemented and will always throw {@link UnsupportedOperationException}.
     * </p>
     *
     * @param expected the first instance to compare for equality
     * @param current the second instance to compare for equality
     *
     * @return never returns normally
     *
     * @throws UnsupportedOperationException always thrown to indicate the method is not implemented
     */
    @Override
    public boolean isEquals(T expected, T current) {
        throw new UnsupportedOperationException();
    }

}