package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Responsible for generate the configuration of the instance.
 *
 * @author Jonathan Pereira
 * @see DiffBuilder
 * @see DiffInstanceBuilder
 * @see DiffMappingBuilder
 * @since 1.0.0
 */
final class DiffConfigurationImpl implements DiffConfiguration {

    private final Map<String, DiffMetadata> metadatas;
    private final List<DiffMetadata> diffMetadatas;

    DiffConfigurationImpl(Map<String, DiffMetadata> metadatas) {
        this.metadatas = metadatas;
        this.diffMetadatas = new ArrayList<>(metadatas.keySet().size());
    }

    /**
     * Gets the configuration for the instance instance.
     *
     * @return the metadata generated by the instance instance.
     */
    @Override
    public List<DiffMetadata> build() {
        if (diffMetadatas.isEmpty()) {
            boolean sortable = false;

            for (Map.Entry<String, DiffMetadata> entry : metadatas.entrySet()) {
                DiffOrder annotation = entry.getValue().getMethod().getAnnotation(DiffOrder.class);

                if (annotation != null) {
                    entry.getValue().setOrder(annotation.value());
                    sortable = true;
                }

                diffMetadatas.add(entry.getValue());
            }

            if (sortable) {
                Collections.sort(diffMetadatas);
            }
        }

        return diffMetadatas;
    }

}
