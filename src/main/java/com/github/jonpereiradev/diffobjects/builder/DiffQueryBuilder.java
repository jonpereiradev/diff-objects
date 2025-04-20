package com.github.jonpereiradev.diffobjects.builder;


/**
 * Builder responsible for modifying the queried fields of a class to create a diff configuration.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public interface DiffQueryBuilder<T> {

    /**
     * Gets the object responsible for querying mappings for changes.
     *
     * @param name the name of the object mapped in the builder.
     *
     * @return the instance of the builder.
     */
    DiffQueryFoundBuilder<T> find(String name);

}
