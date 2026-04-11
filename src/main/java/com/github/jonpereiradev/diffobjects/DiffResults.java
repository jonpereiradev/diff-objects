package com.github.jonpereiradev.diffobjects;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents a collection of {@link DiffResult} instances produced by a diff operation.
 * <p>
 * Provides convenient methods for accessing, streaming, and iterating over the results.
 * This interface extends {@link Iterable} to allow enhanced-for loop usage and supports
 * additional utility methods for working with the diff results.
 * </p>
 *
 * <p>Typical usage:</p>
 * <pre>
 *     DiffResults results = ...;
 *     for (DiffResult result : results) {
 *         // process each result
 *     }
 * </pre>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public interface DiffResults extends Iterable<DiffResult> {

    /**
     * Returns the list of all {@link DiffResult} instances in this collection.
     *
     * @return an unmodifiable list of diff results
     */
    List<DiffResult> getResults();

    /**
     * Returns a sequential {@link Stream} of the diff results.
     *
     * @return a stream of {@link DiffResult} elements
     */
    default Stream<DiffResult> stream() {
        return getResults().stream();
    }

    /**
     * Checks if this collection contains no diff results.
     *
     * @return {@code true} if there are no results; {@code false} otherwise
     */
    default boolean isEmpty() {
        return getResults().isEmpty();
    }

    /**
     * Returns the number of diff results in this collection.
     *
     * @return the size of the results list
     */
    default int size() {
        return getResults().size();
    }

    /**
     * Returns an iterator over the diff results.
     *
     * @return an {@link Iterator} of {@link DiffResult}
     */
    @Override
    default Iterator<DiffResult> iterator() {
        return getResults().iterator();
    }

    /**
     * Performs the given action for each diff result in this collection.
     *
     * @param action the action to be performed for each element
     */
    @Override
    default void forEach(Consumer<? super DiffResult> action) {
        getResults().forEach(action);
    }

    /**
     * Returns a {@link Spliterator} over the diff results.
     *
     * @return a {@link Spliterator} of {@link DiffResult}
     */
    @Override
    default Spliterator<DiffResult> spliterator() {
        return getResults().spliterator();
    }

}