package com.github.jonpereiradev.diffobjects.comparator;

/**
 * Comparator implementation that checks two strings for equality using the {@code equalsIgnoreCase} method.
 * <p>
 * This comparator is useful for case-insensitive string comparisons in diff operations.
 * Returns {@code true} if the expected string is not {@code null} and equals the current string,
 * ignoring case considerations.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.2.0
 */
public class EqualsIgnoreCaseComparator implements DiffComparator<String> {

    /**
     * Checks the equality of two strings, ignoring case considerations.
     *
     * @param expected the string with the expected state
     * @param current the string with the current state
     *
     * @return {@code true} if the two strings are equal ignoring case, {@code false} otherwise
     */
    @Override
    public boolean isEquals(String expected, String current) {
        return expected != null && expected.equalsIgnoreCase(current);
    }

}