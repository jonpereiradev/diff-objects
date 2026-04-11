package com.github.jonpereiradev.diffobjects.builder;

/**
 * Provides methods to configure the types of field mappings supported for diff operations.
 * <p>
 * This builder interface allows you to select between mapping all fields via reflection,
 * mapping only fields with supported annotations, or manually specifying field mappings.
 * </p>
 *
 * @param <T> the type of object for which the mapping is being configured
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffMappingTypeBuilder<T> {

    /**
     * Identifies and maps all fields of a class using reflection.
     *
     * @return a {@link DiffMappingBuilder} instance with all fields mapped
     */
    DiffMappingBuilder<T> all();

    /**
     * Identifies and maps all fields of a class that have supported annotations.
     *
     * @return a {@link DiffMappingBuilder} instance with only annotated fields mapped
     */
    DiffMappingBuilder<T> annotations();

    /**
     * Enables manual mapping of fields for diff operations.
     *
     * @return a {@link DiffManualMappingBuilder} instance for manual field mapping configuration
     */
    DiffManualMappingBuilder<T> fields();

}