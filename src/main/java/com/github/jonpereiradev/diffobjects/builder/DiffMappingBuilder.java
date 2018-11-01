package com.github.jonpereiradev.diffobjects.builder;

/**
 * Builder responsible for mapping the fields of a class to create a configuration of diff.
 *
 * @author Jonathan Pereira
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffConfiguration
 * @since 1.0
 */
public interface DiffMappingBuilder {

    /**
     * Maps the getter of the field for the class.
     *
     * @param field name of the field that will me used to find the getter method.
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder mapping(String field);

    /**
     * Maps the getter of the field for the class with the nestedField property to allow deep diff.
     *
     * @param field name of the field that will me used to find the getter method.
     * @param nestedField the nested property of the object to make the diff.
     * @return the instance of this mapping.
     */
    DiffQueryMappingBuilder mapping(String field, String nestedField);

    /**
     * Gets the configuration instance to get the configuration generated by this instance instance.
     *
     * @return a configuration instance instance.
     */
    DiffConfiguration configuration();

}
