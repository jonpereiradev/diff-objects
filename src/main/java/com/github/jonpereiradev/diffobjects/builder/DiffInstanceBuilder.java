package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Builder with the methods of a instance instance.
 *
 * @author Jonathan Pereira
 * @see DiffBuilder
 * @see DiffMappingBuilder
 * @see DiffConfiguration
 * @since 1.0
 */
public interface DiffInstanceBuilder<T> {

    /**
     * Maps all the field of a class.
     *
     * @return the instance responsible for this mapping.
     */
    DiffMappingAllBuilder mappingAll();

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
     * @param field name of the field that will me used to find the getter method.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    <F> DiffQueryMappingBuilder<T> mapping(String field, Class<F> fieldClass, DiffComparator<F> comparator);

}
