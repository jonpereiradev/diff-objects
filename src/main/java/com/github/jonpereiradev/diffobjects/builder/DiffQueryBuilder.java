package com.github.jonpereiradev.diffobjects.builder;


/**
 * Builder responsible for modify the queried field of a class to create a configuration of diff.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */
public interface DiffQueryBuilder<T> {

    /**
     * Gets the object responsible for query mappings for change.
     *
     * @param name the name of the object mapped in the builder.
     *
     * @return the instance of the builder.
     */
    DiffQueryFoundBuilder<T> find(String name);

}
