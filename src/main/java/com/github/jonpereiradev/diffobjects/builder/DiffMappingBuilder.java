package com.github.jonpereiradev.diffobjects.builder;

/**
 * Builder responsible for mapping the fields of a class to create a configuration of diff.
 *
 * @author Jonathan Pereira
 * @since 1.0
 *
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffConfiguration
 */
public interface DiffMappingBuilder {

    /**
     * Returns to the instance to allow the fluent interface.
     *
     * @return the instance responsible for this mapping.
     */
    DiffInstanceBuilder instance();

    /**
     * Maps all the field of a class.
     *
     * @return the instance responsible for this mapping.
     */
    DiffInstanceBuilder mappingAll();

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     *
     * @return the instance of this mapping.
     */
    DiffQueryBuilder mapping(String field);

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     *
     * @return the instance of this mapping.
     */
    DiffQueryBuilder mapping(String field, String value);

    /**
     * Remove a mapping of the field for the class.
     *
     * @param field name of the field that will me used to remove.
     *
     * @return the instance of this mapping.
     */
    DiffMappingBuilder unmapping(String field);

}
