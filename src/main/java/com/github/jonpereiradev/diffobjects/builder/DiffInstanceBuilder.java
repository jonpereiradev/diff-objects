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
public interface DiffInstanceBuilder {

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
    DiffQueryMappingBuilder mapping(String field);

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder mapping(String field, DiffComparator comparator);

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     *
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder mapping(String field, String nestedField);

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     * @param comparator implementation that define how two objects will be check for equality.
     *
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder mapping(String field, String nestedField, DiffComparator comparator);

}
