package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffConfig;

/**
 * Builder responsible for modifying the queried field of a class to create a diff configuration.
 *
 * @author Jonathan Pereira
 * @see DiffConfigBuilderImpl
 * @see DiffConfigBuilder
 * @see DiffConfig
 * @since 1.0.0
 */
public interface DiffQueryFoundBuilder<T> extends DiffQueryBuilder<T> {

    /**
     * Defines a property for the last mapping.
     *
     * @param key the identifier of the property.
     * @param value the value of the property.
     *
     * @return the instance of this builder.
     */
    DiffQueryFoundBuilder<T> property(String key, String value);

    /**
     * Removes the mapping that this current query represents.
     *
     * @return the instance of this builder.
     */
    DiffQueryBuilder<T> ignore();

    /**
     * Gets the configuration instance that generates the configuration for this instance.
     *
     * @return a configuration instance.
     */
    DiffConfig build();

}
