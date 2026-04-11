package com.github.jonpereiradev.diffobjects.builder;

/**
 * Builder interface responsible for modifying the queried fields of a class to create a diff configuration.
 * <p>
 * Provides methods to locate and modify field mappings by name, enabling fine-grained control over
 * which fields are included or excluded in the diff configuration.
 * </p>
 *
 * @param <T> the type of object being configured for diff operations
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public interface DiffQueryBuilder<T> {

    /**
     * Finds the mapping for a field by its name and returns a builder for further modifications.
     *
     * @param name the name of the field mapped in the builder
     *
     * @return a {@link DiffQueryFoundBuilder} instance for modifying the found field mapping
     */
    DiffQueryFoundBuilder<T> find(String name);

}