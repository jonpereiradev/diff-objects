package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;

/**
 * Contract of a class with mapped fields for final operations.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingBuilder<T> {

    /**
     * Allows the change of the current mapped fields.
     *
     * @return instance for search mapped fields for mutable operations.
     */
    DiffQueryBuilder<T> query();

    /**
     * Constructs the final configuration object with the mapped fields.
     *
     * @return instance of the configuration for the mapped fields.
     */
    DiffConfig build();

}
