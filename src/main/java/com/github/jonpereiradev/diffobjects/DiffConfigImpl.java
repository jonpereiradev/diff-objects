package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.builder.DiffBuilderContext;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Responsible for generate the configuration of the instance.
 *
 * @author Jonathan Pereira
 * @since 1.0.0
 */

public final class DiffConfigImpl implements DiffConfig {

    private final DiffBuilderContext<?> context;
    private final List<DiffMetadata> metadataList;

    public DiffConfigImpl(DiffBuilderContext<?> context) {
        this.context = context;        
        this.metadataList = new ArrayList<>(context.getMetadataMap().size());
    }

    /**
     * Gets the configuration for the instance.
     *
     * @return the metadata generated by the instance.
     */
    @Override
    public List<DiffMetadata> build() {
        if (metadataList.isEmpty()) {
            boolean sortable = false;

            for (Map.Entry<String, DiffMetadata> entry : context.getMetadataMap().entrySet()) {
                DiffOrder annotation = entry.getValue().getMethod().getAnnotation(DiffOrder.class);

                if (annotation != null) {
                    entry.getValue().setOrder(annotation.value());
                    sortable = true;
                }

                metadataList.add(entry.getValue());
            }

            if (sortable) {
                Collections.sort(metadataList);
            }
        }

        return metadataList;
    }

}