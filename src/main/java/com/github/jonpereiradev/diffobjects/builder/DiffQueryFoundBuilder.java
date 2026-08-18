package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;

/**
 * Builder interface responsible for modifying a specific queried field mapping in a diff configuration.
 * <p>
 * Allows setting custom properties for the field, removing the field from the mapping,
 * and building the resulting {@link DiffConfig} instance.
 * </p>
 *
 * @param <T> the type of object being configured for diff operations
 *
 * @author Jonathan Pereira
 * @see DiffConfigBuilderImpl
 * @see DiffConfigBuilder
 * @see DiffConfig
 * @since 1.0.0
 */
public interface DiffQueryFoundBuilder<T> extends DiffQueryBuilder<T> {

    /**
     * Sets a custom property for the currently queried field mapping in the diff configuration.
     *
     * @param key the identifier of the property
     * @param value the value of the property
     *
     * @return this builder instance for fluent chaining
     */
    DiffQueryFoundBuilder<T> property(String key, String value);

    /**
     * Removes the mapping for the field represented by this query from the diff configuration.
     *
     * @return a {@link DiffQueryBuilder} instance for further configuration
     */
    DiffQueryBuilder<T> ignore();

    /**
     * Builds and returns the {@link DiffConfig} instance representing the current configuration.
     *
     * @return a {@link DiffConfig} instance with the applied modifications
     */
    DiffConfig build();

}