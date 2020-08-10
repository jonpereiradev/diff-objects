package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Builder responsible for mapping the fields of a class to create a configuration of diff.
 *
 * @author Jonathan Pereira
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffConfiguration
 * @since 1.0.0
 */
public interface DiffMappingBuilder<T> {

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     *
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder<T> mapping(String field);

    /**
     * Maps the getter of the field for the class.
     *
     * @param <F> the type of object been mapped for the field.
     * @param field name of the field that will me used to find the getter method.
     * @param fieldClass the class type of the field been mapped.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    <F> DiffQueryMappingBuilder<T> mapping(String field, Class<F> fieldClass, DiffComparator<F> comparator);

    /**
     * Gets the configuration instance to get the configuration generated by this instance instance.
     *
     * @return a configuration instance instance.
     */
    DiffConfiguration configuration();

}
