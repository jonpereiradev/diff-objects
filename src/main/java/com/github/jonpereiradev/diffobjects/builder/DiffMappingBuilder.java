package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;

/**
 * Contract for a class with mapped fields for final operations.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingBuilder<T> {

    /**
     * Allows changing the current mapped fields.
     *
     * @return an instance for searching mapped fields for mutable operations.
     */
    DiffQueryBuilder<T> query();

    /**
     * Constructs the final configuration object with the mapped fields.
     *
     * @return an instance of the configuration for the mapped fields.
     */
    DiffConfig build();

}
