package com.github.jonpereiradev.diffobjects.builder;

/**
 * Builder responsible for mapping the fields of a class to create a configuration of diff.
 *
 * @author jonpereiradev@gmail.com
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffConfigurationBuilder
 */
public interface DiffMappingBuilder {

    /**
     * Returns to the instance instance to allow the fluent interface.
     *
     * @return the instance instance responsible for this mapping.
     */
    DiffInstanceBuilder instance();

    /**
     * Maps all the field of a class.
     *
     * @return the instance instance responsible for this mapping.
     */
    DiffInstanceBuilder mappingAll();

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @return the instance of this mapping instance.
     */
    DiffMappingBuilder mapping(String field);

    /**
     * Maps the getter of the field for the class with the value property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param value the nested property of the object to make the diff.
     * @return the instance of this mapping instance.
     */
    DiffMappingBuilder mapping(String field, String value);

}

