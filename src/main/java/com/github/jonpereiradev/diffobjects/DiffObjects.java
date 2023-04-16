package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.util.Collection;


/**
 * Responsible for check the difference between two objects.
 *
 * @param <T> type of object being compared.
 *
 * @author Jonathan Pereira
 * @see DiffObjectsImpl
 * @since 1.0.0
 */
public interface DiffObjects<T> {

    /**
     * Creates a DiffObjects for a specific class.
     *
     * @param clazz class type of this DiffObjects.
     * @param <T> type of the class.
     *
     * @return the DiffObjects for the class.
     */
    static <T> DiffObjects<T> forClass(Class<T> clazz) {
        return new DiffObjectsImpl<>(clazz);
    }

    /**
     * Execute the diff between two objects.
     * <p>
     * If the class supports annotation diff configuration, it's used in the diff context.
     * Otherwise, makes the diff using all fields of the class.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     *
     * @return the diff result with all configured fields checked.
     */
    DiffResults diff(T expected, T current);

    /**
     * Execute the diff between two objects using a configuration.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param config the diff configuration metadata.
     *
     * @return the diff result with all configured fields checked.
     */
    DiffResults diff(T expected, T current, DiffConfig config);

    /**
     * Execute the diff between two collection of objects.
     * <p>
     * If the class supports annotation diff configuration, it's used in the diff context.
     * Otherwise, makes the diff using all fields of the class.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param matcher compares if one object from the expected collection is representable by one in the current.
     *
     * @return the diff result with all configured fields checked.
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Execute the diff between two collection of objects using the diff config metadata.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param config the diff configuration metadata.
     * @param matcher compares if one object from the expected collection is representable by one in the current.
     *
     * @return the diff result with all configured fields checked.
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

    /**
     * Check if exists the expected and current state are equals.
     * <p>
     * If the class supports annotation diff configuration, it's used in the diff context.
     * Otherwise, makes the diff using all fields of the class.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    boolean isEquals(T expected, T current);

    /**
     * Check if exists the expected and current state are equals.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param config the diff configuration metadata.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    boolean isEquals(T expected, T current, DiffConfig config);

    /**
     * Check if exists the expected and current state are equals.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param matcher compares if one object from the expected collection is representable by one in the current.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Check if exists the expected and current state are equals.
     *
     * @param expected the expected object state to check for diff.
     * @param current the current object state to check for diff.
     * @param config the diff configuration metadata.
     * @param matcher compares if one object from the expected collection is representable by one in the current.
     *
     * @return {@code true} if no difference exists between the objects or {@code false} otherwise.
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

}
