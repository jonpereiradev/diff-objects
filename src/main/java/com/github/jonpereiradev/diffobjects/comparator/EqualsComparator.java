package com.github.jonpereiradev.diffobjects.comparator;

import java.util.Objects;

/**
 * Check two objects for equality using the equals method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.1
 */
public class EqualsComparator implements DiffComparator {

    /**
     * Check the equality of two objects.
     *
     * @param o1 first instance to compare the equality.
     * @param o2 second instance to compare the equality.
     *
     * @return {@code true} if the two objects are equals.
     */
    @Override
    public boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }
}
