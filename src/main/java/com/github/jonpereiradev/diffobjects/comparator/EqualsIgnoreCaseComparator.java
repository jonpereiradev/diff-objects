package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Check two strings for equality using the equals ignore case method implementation.
 *
 * @author Jonathan Pereira
 * @since 1.2.0
 */
public class EqualsIgnoreCaseComparator implements DiffComparator<String> {

    /**
     * Check the equality of two objects.
     *
     * @param expected the object with the expected state.
     * @param current the object with the current state.
     *
     * @return {@code true} if the two objects are equals.
     */
    @Override
    public boolean equals(String expected, String current) {
        return expected.equalsIgnoreCase(current);
    }

}
