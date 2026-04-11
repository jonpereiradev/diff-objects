package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.util.Collection;

/**
 * Provides an API for comparing objects or collections of objects and determining their differences.
 * <p>
 * Supports both annotation-based and programmatic configuration of comparison strategies.
 * Use this interface to perform deep comparisons and retrieve detailed diff results.
 *
 * @param <T> the type of object being compared
 *
 * @author Jonathan Pereira
 * @see DiffObjectsImpl
 * @since 1.0.0
 */
public interface DiffObjects<T> {

    /**
     * Creates a {@code DiffObjects} instance for the specified class type.
     *
     * @param clazz the class type to compare
     * @param <T> the type of the class
     *
     * @return a new {@code DiffObjects} instance for the given class
     */
    static <T> DiffObjects<T> forClass(Class<T> clazz) {
        return new DiffObjectsImpl<>(clazz);
    }

    /**
     * Compares two objects and returns the differences.
     * <p>
     * Uses annotation-based configuration if available; otherwise, compares all fields.
     *
     * @param expected the expected object state
     * @param current the current object state
     *
     * @return a {@link DiffResults} containing all detected differences
     */
    DiffResults diff(T expected, T current);

    /**
     * Compares two objects using the provided diff configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     * @param config the diff configuration to use
     *
     * @return a {@link DiffResults} containing all detected differences
     */
    DiffResults diff(T expected, T current, DiffConfig config);

    /**
     * Compares two collections of objects and returns the differences.
     * <p>
     * Uses annotation-based configuration if available; otherwise, compares all fields.
     *
     * @param expected the expected collection of objects
     * @param current the current collection of objects
     * @param matcher a comparator to match objects between collections
     *
     * @return a {@link DiffResults} containing all detected differences
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Compares two collections of objects using the provided diff configuration.
     *
     * @param expected the expected collection of objects
     * @param current the current collection of objects
     * @param config the diff configuration to use
     * @param matcher a comparator to match objects between collections
     *
     * @return a {@link DiffResults} containing all detected differences
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

    /**
     * Determines if two objects are equal according to the diff strategy.
     * <p>
     * Uses annotation-based configuration if available; otherwise, compares all fields.
     *
     * @param expected the expected object state
     * @param current the current object state
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     */
    boolean isEquals(T expected, T current);

    /**
     * Determines if two objects are equal using the provided diff configuration.
     *
     * @param expected the expected object state
     * @param current the current object state
     * @param config the diff configuration to use
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     */
    boolean isEquals(T expected, T current, DiffConfig config);

    /**
     * Determines if two collections of objects are equal according to the diff strategy.
     *
     * @param expected the expected collection of objects
     * @param current the current collection of objects
     * @param matcher a comparator to match objects between collections
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Determines if two collections of objects are equal using the provided diff configuration.
     *
     * @param expected the expected collection of objects
     * @param current the current collection of objects
     * @param config the diff configuration to use
     * @param matcher a comparator to match objects between collections
     *
     * @return {@code true} if no differences are found; {@code false} otherwise
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

}