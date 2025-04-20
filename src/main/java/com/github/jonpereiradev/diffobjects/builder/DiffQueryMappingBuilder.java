package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Responsible for modifying a mapped property.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffQueryMappingBuilder<T> {

    /**
     * Adds a metadata property for a mapped property.
     *
     * @param key the identifier of the property.
     * @param value the value of the property.
     *
     * @return the instance of the mapped property with the new metadata property.
     */
    DiffQueryMappingBuilder<T> property(String key, String value);

    /**
     * Maps a new property by name.
     *
     * @param name the name that identifies the property for mapping.
     *
     * @return the instance of the mapped property.
     */
    DiffQueryMappingBuilder<T> map(String name);

    /**
     * Maps a property by name using a custom comparator.
     *
     * @param name the name that identifies the property for mapping.
     * @param comparator the custom comparator used to check equality of the property.
     *
     * @return the instance of the mapped property.
     */
    <F> DiffQueryMappingBuilder<T> map(String name, DiffComparator<F> comparator);

    /**
     * Constructs the final configuration object with the mapped fields.
     *
     * @return an instance of the configuration containing the mapped fields.
     */
    DiffConfig build();

}
