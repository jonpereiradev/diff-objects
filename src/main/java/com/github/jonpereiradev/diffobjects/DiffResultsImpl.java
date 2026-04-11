package com.github.jonpereiradev.diffobjects;

import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link DiffResults}, representing an immutable collection of diff results.
 * <p>
 * Stores the results as an unmodifiable list and provides access to them via the {@link #getResults()} method.
 * </p>
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
final class DiffResultsImpl implements DiffResults {

    /**
     * The unmodifiable list of diff results.
     */
    private final List<DiffResult> results;

    /**
     * Constructs a new {@code DiffResultsImpl} with the given list of diff results.
     *
     * @param results the list of diff results to store (will be wrapped as unmodifiable)
     */
    DiffResultsImpl(List<DiffResult> results) {
        this.results = Collections.unmodifiableList(results);
    }

    /**
     * Returns the list of all {@link DiffResult} instances in this collection.
     *
     * @return an unmodifiable list of diff results
     */
    @Override
    public List<DiffResult> getResults() {
        return results;
    }

}