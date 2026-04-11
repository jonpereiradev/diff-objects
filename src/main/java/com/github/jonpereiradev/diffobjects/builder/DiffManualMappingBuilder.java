package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Provides a builder interface for manually mapping fields of an object for diff operations.
 * <p>
 * Allows explicit specification of property mappings by name, with optional custom comparators
 * for property-level equality checks. This interface extends {@link DiffMappingBuilder} to support
 * fluent configuration of manual mappings.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     DiffManualMappingBuilder&lt;MyType&gt; builder = ...;
 *     builder.map("fieldA")
 *            .map("fieldB", new MyCustomComparator());
 * </pre>
 *
 * @param <T> the type of object being mapped
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffManualMappingBuilder<T> extends DiffMappingBuilder<T> {

    /**
     * Maps a property by its name for inclusion in the diff operation.
     *
     * @param name the name that identifies the property for mapping
     *
     * @return this builder instance for fluent chaining
     */
    DiffManualMappingBuilder<T> map(String name);

    /**
     * Maps a property by its name using a custom comparator for equality checks.
     *
     * @param name the name that identifies the property for mapping
     * @param comparator a custom comparator to determine equality for the property
     * @param <C> the type of the property being compared
     *
     * @return this builder instance for fluent chaining
     */
    <C> DiffManualMappingBuilder<T> map(String name, DiffComparator<C> comparator);

}