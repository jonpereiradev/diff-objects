package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.comparator.DiffComparator;

/**
 * Responsible for providing a manual mapping of fields.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffManualMappingBuilder<T> extends DiffMappingBuilder<T> {

    /**
     * Maps a property by name.
     *
     * @param name the name that identifies the property for mapping.
     *
     * @return an instance of the mapped property.
     */
    DiffManualMappingBuilder<T> map(String name);

    /**
     * Maps a property by name using a custom comparator.
     *
     * @param name the name that identifies the property for mapping.
     * @param comparator a custom comparator that checks the property for equality.
     *
     * @return an instance of the mapped property.
     */
    <C> DiffManualMappingBuilder<T> map(String name, DiffComparator<C> comparator);

}
