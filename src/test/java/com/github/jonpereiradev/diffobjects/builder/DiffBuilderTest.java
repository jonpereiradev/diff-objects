package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DiffBuilderTest {

    @Test(expected = NullPointerException.class)
    public void testDiffBuilderNullClassParameter() {
        DiffBuilder.map(null);
    }

    @Test
    public void testDiffBuilderMappingAll() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mapper()
            .mappingAll()
            .configuration()
            .build();

        Assert.assertNotNull(metadata);
        Assert.assertFalse(metadata.isEmpty());
        Assert.assertEquals(2, metadata.size());
        Assert.assertEquals("getName", metadata.get(0).getMethod().getName());
        Assert.assertEquals("getParent", metadata.get(1).getMethod().getName());
    }

    @Test(expected = IllegalStateException.class)
    public void testDiffBuilderMappingAllInvalidState() {
        DiffBuilder
            .map(ObjectElement.class)
            .mapper()
            .mapping("name")
            .mappingAll()
            .configuration()
            .build();
    }

    @Test
    public void testDiffBuilderMappingFieldName() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mapper()
            .mapping("name")
            .instance()
            .configuration()
            .build();

        Assert.assertNotNull(metadata);
        Assert.assertFalse(metadata.isEmpty());
        Assert.assertEquals(1, metadata.size());
        Assert.assertEquals("getName", metadata.get(0).getMethod().getName());
    }

    @Test
    public void testDiffBuilderMappingFieldParent() {
        List<DiffMetadata> metadata = DiffBuilder
            .map(ObjectElement.class)
            .mapper()
            .mapping("parent")
            .instance()
            .configuration()
            .build();

        Assert.assertNotNull(metadata);
        Assert.assertFalse(metadata.isEmpty());
        Assert.assertEquals(1, metadata.size());
        Assert.assertEquals("getParent", metadata.get(0).getMethod().getName());
    }

    @Test(expected = IllegalStateException.class)
    public void testDiffBuilderMappingDuplicated() {
        DiffBuilder.map(ObjectElement.class).mapper().mapping("name").mapping("name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiffBuilderMappingNotFound() {
        DiffBuilder.map(ObjectElement.class).mapper().mapping("notExists");
    }

    @Test
    public void testDiffBuilderSingleStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ObjectElement.class)
            .mapper()
            .mapping("name")
            .mapping("parent")
            .instance()
            .configuration()
            .build();

        Assert.assertNotNull(diffMetadatas);
        Assert.assertFalse(diffMetadatas.isEmpty());
        Assert.assertEquals(2, diffMetadatas.size());
        Assert.assertEquals("getName", diffMetadatas.get(0).getMethod().getName());
        Assert.assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadatas.get(0).getStrategy());
        Assert.assertEquals("getParent", diffMetadatas.get(1).getMethod().getName());
        Assert.assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadatas.get(1).getStrategy());
    }

    @Test
    public void testDiffBuilderDeepStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ComplexElement.class)
            .mapper()
            .mapping("objectElement", "name")
            .instance()
            .configuration()
            .build();

        Assert.assertNotNull(diffMetadatas);
        Assert.assertFalse(diffMetadatas.isEmpty());
        Assert.assertEquals(1, diffMetadatas.size());
        Assert.assertEquals("getObjectElement", diffMetadatas.get(0).getMethod().getName());
        Assert.assertSame(DiffStrategyType.DEEP.getStrategy(), diffMetadatas.get(0).getStrategy());
    }

    @Test
    public void testDiffBuilderCollectionStrategyType() {
        List<DiffMetadata> diffMetadatas = DiffBuilder
            .map(ComplexElement.class)
            .mapper()
            .mapping("objectElementList", "name")
            .instance()
            .configuration()
            .build();

        Assert.assertNotNull(diffMetadatas);
        Assert.assertFalse(diffMetadatas.isEmpty());
        Assert.assertEquals(1, diffMetadatas.size());
        Assert.assertEquals("getObjectElementList", diffMetadatas.get(0).getMethod().getName());
        Assert.assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadatas.get(0).getStrategy());
    }
}
