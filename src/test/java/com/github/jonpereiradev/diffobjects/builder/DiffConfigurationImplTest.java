package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.ObjectElement;
import com.github.jonpereiradev.diffobjects.annotation.DiffOrder;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DiffConfigurationImplTest {

    @Test
    public void testMustBuildEmptyWithEmptyMetadatas() {
        DiffConfiguration diffConfiguration = new DiffConfigurationImpl(new HashMap<String, DiffMetadata>());
        assertTrue(diffConfiguration.build().isEmpty());
    }

    @Test
    public void testMustBuildUnorderedMetadataWithoutOrderAnnotation() throws NoSuchMethodException {
        Map<String, DiffMetadata> map = new HashMap<>();
        DiffMetadata o2 = new DiffMetadata("parent", ObjectElement.class.getMethod("getParent"), DiffStrategyType.SINGLE, new EqualsComparator());
        DiffMetadata o1 = new DiffMetadata("name", ObjectElement.class.getMethod("getName"), DiffStrategyType.SINGLE, new EqualsComparator());

        map.put(o1.getValue(), o1);
        map.put(o2.getValue(), o2);

        DiffConfiguration diffConfiguration = new DiffConfigurationImpl(map);
        List<DiffMetadata> metadatas = diffConfiguration.build();

        assertTrue(metadatas.contains(o1));
        assertTrue(metadatas.contains(o2));
    }

    @Test
    public void testMustBuildOrderedMetadataWithOrderAnnotation() throws NoSuchMethodException {
        Map<String, DiffMetadata> map = new HashMap<>();
        DiffMetadata o2 = new DiffMetadata("value1", ObjectElementOrdered.class.getMethod("getValue1"), DiffStrategyType.SINGLE, new EqualsComparator());
        DiffMetadata o1 = new DiffMetadata("value2", ObjectElementOrdered.class.getMethod("getValue2"), DiffStrategyType.SINGLE, new EqualsComparator());

        map.put(o1.getValue(), o1);
        map.put(o2.getValue(), o2);

        DiffConfiguration diffConfiguration = new DiffConfigurationImpl(map);
        List<DiffMetadata> metadatas = diffConfiguration.build();

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