package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class DiffBuilderTest {

    @Test(expected = NullPointerException.class)
    public void testDiffBuilderNullClassParameter() {
        DiffBuilder.map(null);
    }

    @Test
    public void testDiffBuilderMappingAll() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mappingAll()
            .configuration()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(2, metadata.size());
        assertEquals("getName", metadata.get(0).getMethod().getName());
        assertEquals("getParent", metadata.get(1).getMethod().getName());
    }

    @Test(expected = DiffException.class)
    public void testDiffBuilderMappingNotFound() {
        DiffBuilder.map(ObjectElement.class).mapping("notExists");
    }

    @Test
    public void testDiffBuilderMappingFieldName() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mapping("name")
            .configuration()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertEquals("getName", metadata.get(0).getMethod().getName());
    }

    @Test
    public void testDiffBuilderMappingFieldParent() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mapping("parent")
            .configuration()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertEquals("getParent", metadata.get(0).getMethod().getName());
    }

    @Test
    public void testDiffBuilderMappingWithQueryProperty() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mappingAll()
            .query("name")
            .unmapping()
            .query("parent")
            .property("query", "true")
            .configuration()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertTrue(metadata.get(0).getProperties().containsKey("query"));
        assertEquals("true", metadata.get(0).getProperties().get("query"));
    }

    @Test
    public void testDiffBuilderSingleStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ObjectElement.class)
            .mapping("name", String.class, new EqualsComparator<>())
            .mapping("parent", String.class)
            .configuration()
            .build();

        assertNotNull(diffMetadatas);
        assertFalse(diffMetadatas.isEmpty());
        assertEquals(2, diffMetadatas.size());
        assertEquals("getName", diffMetadatas.get(0).getMethod().getName());
        assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadatas.get(0).getStrategy());
        assertEquals("getParent", diffMetadatas.get(1).getMethod().getName());
        assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadatas.get(1).getStrategy());
    }

    @Test
    public void testDiffBuilderDeepStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ComplexElement.class)
            .mapping("objectElement.name")
            .configuration()
            .build();

        assertNotNull(diffMetadatas);
        assertFalse(diffMetadatas.isEmpty());
        assertEquals(1, diffMetadatas.size());
        assertEquals("getObjectElement", diffMetadatas.get(0).getMethod().getName());
        assertSame(DiffStrategyType.NESTED.getStrategy(), diffMetadatas.get(0).getStrategy());
    }

    @Test
    public void testDiffBuilderCollectionStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ComplexElement.class)
            .mapping("objectElementList.name")
            .configuration()
            .build();

        assertNotNull(diffMetadatas);
        assertFalse(diffMetadatas.isEmpty());
        assertEquals(1, diffMetadatas.size());
        assertEquals("getObjectElementList", diffMetadatas.get(0).getMethod().getName());
        assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadatas.get(0).getStrategy());
    }

    @Test
    public void testDiffBuilderDefaultEqualsComparator() {
        List<DiffMetadata> diffMetadata = DiffBuilder.map(ObjectElement.class).mapping("name").configuration().build();

        assertNotNull(diffMetadata);
        assertFalse(diffMetadata.isEmpty());
        assertEquals(1, diffMetadata.size());
        assertEquals("getName", diffMetadata.get(0).getMethod().getName());
        assertTrue(diffMetadata.get(0).getComparator() instanceof EqualsComparator);
    }
}
