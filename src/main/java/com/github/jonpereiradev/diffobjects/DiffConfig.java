package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.List;


/**
 * Contract for the diff configuration object that maps the properties being checked for differences.
 *
 * @author Jonathan Pereira
 * @version 1.2.0
 * @since 1.0.0
 */
public interface DiffConfig {

    /**
     * Builds the configuration for the instance and generates the corresponding metadata.
     *
     * @return the generated metadata for the instance
     */
    List<DiffMetadata> build();

}
