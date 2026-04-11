package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Builder interface responsible for modifying the mapping of a specific property in a diff configuration.
 * <p>
 * Allows adding metadata properties to a mapped property, mapping new properties by name (optionally with a custom comparator),
 * and building the final {@link DiffConfig} instance with the configured mappings.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     DiffQueryMappingBuilder&lt;MyType&gt; builder = ...;
 *     builder.property("description", "Primary key")
 *            .map("fieldA")
 *            .map("fieldB", new MyCustomComparator())
 *            .build();
 * </pre>
 *
 * @param <T> the type of object being configured for diff operations
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffQueryMappingBuilder<T> {

    /**
     * Adds a metadata property for the currently mapped property.
     *
     * @param key the identifier of the property
     * @param value the value of the property
     *
     * @return this builder instance for fluent chaining
     */
    DiffQueryMappingBuilder<T> property(String key, String value);

    /**
     * Maps a new property by its name for inclusion in the diff operation.
     *
     * @param name the name that identifies the property for mapping
     *
     * @return this builder instance for fluent chaining
     */
    DiffQueryMappingBuilder<T> map(String name);

    /**
     * Maps a property by its name using a custom comparator for equality checks.
     *
     * @param name the name that identifies the property for mapping
     * @param comparator the custom comparator used to check equality of the property
     * @param <F> the type of the property being compared
     *
     * @return this builder instance for fluent chaining
     */
    <F> DiffQueryMappingBuilder<T> map(String name, DiffComparator<F> comparator);

    /**
     * Builds and returns the final {@link DiffConfig} instance with the mapped fields.
     *
     * @return a {@link DiffConfig} instance containing the mapped fields
     */
    DiffConfig build();

}