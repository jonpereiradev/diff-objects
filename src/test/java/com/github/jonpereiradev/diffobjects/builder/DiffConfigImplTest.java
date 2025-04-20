package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffConfigImpl;
import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiffConfigImplTest {

    @Test
    void testMustBuildEmptyWithEmptyMetadatas() {
        DiffBuilderContext<ObjectElement> context = new DiffBuilderContext<>(ObjectElement.class);
        DiffConfig diffConfig = new DiffConfigImpl(context);
        assertTrue(diffConfig.build().isEmpty());
    }

    @Test
    void testMustBuildUnorderedMetadataWithoutOrderAnnotation() throws NoSuchMethodException {
        DiffBuilderContext<ObjectElement> context = new DiffBuilderContext<>(ObjectElement.class);
        DiffMetadata o2 = new DiffMetadata("parent", ObjectElement.class.getMethod("getParent"), DiffStrategyType.SINGLE, new EqualsComparator<>());
        DiffMetadata o1 = new DiffMetadata("name", ObjectElement.class.getMethod("getName"), DiffStrategyType.SINGLE, new EqualsComparator<>());

        context.put(o1.getValue(), o1);
        context.put(o2.getValue(), o2);

        DiffConfig diffConfig = new DiffConfigImpl(context);
        List<DiffMetadata> metadatas = diffConfig.build();

        assertTrue(metadatas.contains(o1));
        assertTrue(metadatas.contains(o2));
    }

    @Test
    void testMustBuildOrderedMetadataWithOrderAnnotation() throws NoSuchMethodException {
        DiffBuilderContext<ObjectElementOrdered> context = new DiffBuilderContext<>(ObjectElementOrdered.class);
        DiffMetadata o2 = new DiffMetadata("value1", ObjectElementOrdered.class.getMethod("getValue1"), DiffStrategyType.SINGLE, new EqualsComparator<>());
        DiffMetadata o1 = new DiffMetadata("value2", ObjectElementOrdered.class.getMethod("getValue2"), DiffStrategyType.SINGLE, new EqualsComparator<>());

        context.put(o1.getValue(), o1);
        context.put(o2.getValue(), o2);

        DiffConfig diffConfig = new DiffConfigImpl(context);
        List<DiffMetadata> metadatas = diffConfig.build();

        assertEquals("value2", metadatas.get(0).getValue());
        assertEquals("value1", metadatas.get(1).getValue());
    }

    private static class ObjectElementOrdered {

        private String value1;
        private String value2;

        @DiffOrder(2)
        public String getValue1() {
            return value1;
        }

        @DiffOrder(1)
        public String getValue2() {
            return value2;
        }
    }
}