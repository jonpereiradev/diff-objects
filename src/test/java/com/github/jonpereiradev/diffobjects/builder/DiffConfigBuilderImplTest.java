package com.github.jonpereiradev.diffobjects.builder;


import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class DiffConfigBuilderImplTest {

    @Test(expected = NullPointerException.class)
    public void testDiffBuilderNullClassParameter() {
        DiffConfigBuilder.forClass(null);
    }

    @Test
    public void testDiffBuilderMappingAll() {
        List<DiffMetadata> metadata = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .all()
            .build()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(2, metadata.size());
        assertEquals("getName", metadata.get(0).getMethod().getName());
        assertEquals("getParent", metadata.get(1).getMethod().getName());
    }

    @Test(expected = DiffException.class)
    public void testDiffBuilderMappingNotFound() {
        DiffConfigBuilder.forClass(ObjectElement.class).mapping().fields().map("notExists");
    }

    @Test
    public void testDiffBuilderMappingFieldName() {
        List<DiffMetadata> metadata = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .fields()
            .map("name")
            .build()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertEquals("getName", metadata.get(0).getMethod().getName());
    }

    @Test
    public void testDiffBuilderMappingFieldParent() {
        List<DiffMetadata> metadata = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .fields()
            .map("parent")
            .build()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertEquals("getParent", metadata.get(0).getMethod().getName());
    }

    @Test
    public void testDiffBuilderMappingWithQueryProperty() {
        List<DiffMetadata> metadata = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .all()
            .query()
            .find("name")
            .ignore()
            .find("parent")
            .property("query", "true")
            .build()
            .build();

        assertNotNull(metadata);
        assertFalse(metadata.isEmpty());
        assertEquals(1, metadata.size());
        assertTrue(metadata.get(0).getProperties().containsKey("query"));
        assertEquals("true", metadata.get(0).getProperties().get("query"));
    }

    @Test
    public void testDiffBuilderSingleStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .fields()
            .map("name", new EqualsComparator<>())
            .map("parent")
            .build()
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
        List<DiffMetadata> diffMetadatas = DiffConfigBuilder
            .forClass(ComplexElement.class)
            .mapping()
            .fields()
            .map("objectElement.name")
            .build()
            .build();

        assertNotNull(diffMetadatas);
        assertFalse(diffMetadatas.isEmpty());
        assertEquals(1, diffMetadatas.size());
        assertEquals("getObjectElement", diffMetadatas.get(0).getMethod().getName());
        assertSame(DiffStrategyType.NESTED.getStrategy(), diffMetadatas.get(0).getStrategy());
    }

    @Test
    public void testDiffBuilderCollectionStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffConfigBuilder
            .forClass(ComplexElement.class)
            .mapping()
            .fields()
            .map("objectElementList.name")
            .build()
            .build();

        assertNotNull(diffMetadatas);
        assertFalse(diffMetadatas.isEmpty());
        assertEquals(1, diffMetadatas.size());
        assertEquals("getObjectElementList", diffMetadatas.get(0).getMethod().getName());
        assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadatas.get(0).getStrategy());
    }

    @Test
    public void testDiffBuilderDefaultEqualsComparator() {
        List<DiffMetadata> diffMetadata = DiffConfigBuilder
            .forClass(ObjectElement.class)
            .mapping()
            .fields()
            .map("name")
            .build()
            .build();

        assertNotNull(diffMetadata);
        assertFalse(diffMetadata.isEmpty());
        assertEquals(1, diffMetadata.size());
        assertEquals("getName", diffMetadata.get(0).getMethod().getName());
        assertTrue(diffMetadata.get(0).getComparator() instanceof EqualsComparator);
    }
}
