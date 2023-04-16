package com.github.jonpereiradev.diffobjects.builder;


/**
 * Responsible for make available the supported types of mappings.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingTypeBuilder<T> {

    /**
     * Identifies and maps all fields from a class using Reflection.
     *
     * @return instance with all the fields mapped.
     */
    DiffMappingBuilder<T> all();

    /**
     * Identifies and maps all fields from a class with supported annotations.
     *
     * @return instance with all the fields with supported annotations mapped.
     */
    DiffMappingBuilder<T> annotations();

    /**
     * Make available a manually mapping of fields.
     *
     * @return instance for manully configure mappings for fields.
     */
    DiffManualMappingBuilder<T> fields();

}
