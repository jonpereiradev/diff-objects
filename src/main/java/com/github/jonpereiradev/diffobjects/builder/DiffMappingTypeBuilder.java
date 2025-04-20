package com.github.jonpereiradev.diffobjects.builder;


/**
 * Responsible for making available the supported types of mappings.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingTypeBuilder<T> {

    /**
     * Identifies and maps all fields of a class using Reflection.
     *
     * @return an instance with all the fields mapped.
     */
    DiffMappingBuilder<T> all();

    /**
     * Identifies and maps all fields of a class with supported annotations.
     *
     * @return an instance with all the fields with supported annotations mapped.
     */
    DiffMappingBuilder<T> annotations();

    /**
     * Makes a manual mapping of fields available.
     *
     * @return an instance for manually configuring mappings for fields.
     */
    DiffManualMappingBuilder<T> fields();

}
