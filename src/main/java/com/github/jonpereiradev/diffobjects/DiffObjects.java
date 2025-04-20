package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

import java.util.Collection;


/**
 * Responsible for checking the difference between two objects.
 *
 * @param <T> the type of object being compared
 *
 * @author Jonathan Pereira
 * @see DiffObjectsImpl
 * @since 1.0.0
 */
public interface DiffObjects<T> {

    /**
     * Creates a DiffObjects instance for a specific class.
     *
     * @param clazz the class type for this DiffObjects.
     * @param <T> the type of the class.
     *
     * @return the DiffObjects instance for the class
     */
    static <T> DiffObjects<T> forClass(Class<T> clazz) {
        return new DiffObjectsImpl<>(clazz);
    }

    /**
     * Executes the diff between two objects.
     * <p>
     * If the class supports annotation-based diff configuration, it is used in the diff context.
     * Otherwise, the diff is performed using all fields of the class.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     *
     * @return the diff result with all configured fields checked
     */
    DiffResults diff(T expected, T current);

    /**
     * Executes the diff between two objects using a configuration.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     * @param config the diff configuration metadata
     *
     * @return the diff result with all configured fields checked
     */
    DiffResults diff(T expected, T current, DiffConfig config);

    /**
     * Executes the diff between two collections of objects.
     * <p>
     * If the class supports annotation-based diff configuration, it is used in the diff context.
     * Otherwise, the diff is performed using all fields of the class.
     *
     * @param expected the expected state of the objects in the collection to check for differences
     * @param current the current state of the objects in the collection to check for differences
     * @param matcher compares whether an object from the expected collection is represented by an object in the current collection
     *
     * @return the diff result with all configured fields checked
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Executes the diff between two collections of objects using the diff configuration metadata.
     *
     * @param expected the expected state of the objects in the collection to check for differences
     * @param current the current state of the objects in the collection to check for differences
     * @param config the diff configuration metadata
     * @param matcher compares whether an object from the expected collection is represented by an object in the current collection
     *
     * @return the diff result with all configured fields checked
     */
    DiffResults diff(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

    /**
     * Checks if the expected and current states are equal.
     * <p>
     * If the class supports annotation-based diff configuration, it is used in the diff context.
     * Otherwise, the diff is performed using all fields of the class.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     *
     * @return {@code true} if no difference exists between the objects, {@code false} otherwise
     */
    boolean isEquals(T expected, T current);

    /**
     * Checks if the expected and current states are equal.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     * @param config the diff configuration metadata
     *
     * @return {@code true} if no difference exists between the objects, {@code false} otherwise
     */
    boolean isEquals(T expected, T current, DiffConfig config);

    /**
     * Checks if the expected and current states are equal.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     * @param matcher compares whether an object from the expected collection is represented by an object in the current collection
     *
     * @return {@code true} if no difference exists between the objects, {@code false} otherwise
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffComparator<T> matcher);

    /**
     * Checks if the expected and current states are equal.
     *
     * @param expected the expected state of the object to check for differences
     * @param current the current state of the object to check for differences
     * @param config the diff configuration metadata
     * @param matcher compares whether an object from the expected collection is represented by an object in the current collection
     *
     * @return {@code true} if no difference exists between the objects, {@code false} otherwise
     */
    boolean isEquals(Collection<T> expected, Collection<T> current, DiffConfig config, DiffComparator<T> matcher);

}
