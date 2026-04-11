package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.List;

/**
 * Defines the contract for configuring which object properties are compared during a diff operation.
 * <p>
 * Implementations specify the fields and comparison strategies to be used when detecting differences
 * between objects. This interface is intended to be implemented by classes that build and provide
 * metadata describing the properties to be checked for differences.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     DiffConfig config = ...;
 *     List&lt;DiffMetadata&gt; metadata = config.build();
 * </pre>
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffConfig {

    /**
     * Builds and returns an unmodifiable list of metadata describing the properties to be compared.
     * Each {@link DiffMetadata} instance defines a field and its associated comparison strategy.
     *
     * @return an unmodifiable list of {@link DiffMetadata} representing the diff configuration
     *
     * @since 1.0.0
     */
    List<DiffMetadata> build();

}