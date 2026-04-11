package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;

/**
 * Defines the contract for a builder that manages mapped fields for diff operations.
 * <p>
 * This interface provides methods to query and modify the current field mappings,
 * as well as to construct the final {@link DiffConfig} instance representing the configuration
 * for the diff operation.
 * </p>
 *
 * @param <T> the type of object being configured for diff mapping
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingBuilder<T> {

    /**
     * Provides access to query and modify the currently mapped fields.
     *
     * @return a {@link DiffQueryBuilder} instance for searching and mutating mapped fields
     */
    DiffQueryBuilder<T> query();

    /**
     * Builds and returns the final {@link DiffConfig} instance with the mapped fields.
     *
     * @return a {@link DiffConfig} representing the configuration for the mapped fields
     */
    DiffConfig build();

}