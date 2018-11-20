package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class DiffMappingBuilderImplTest {

    @Test
    public void testMustMapFieldWithSingleStrategy() {
        HashMap<String, DiffMetadata> metadatas = new HashMap<>();
        DiffMappingBuilder diffMappingBuilder = new DiffMappingBuilderImpl(ComplexElement.class, metadatas);

        diffMappingBuilder.mapping("parent");

        DiffMetadata diffMetadata = diffMappingBuilder.configuration().build().get(0);

        assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getParent", diffMetadata.getMethod().getName());
    }

    @Test
    public void testMustMapFieldWithDeepStrategy() {
        HashMap<String, DiffMetadata> metadatas = new HashMap<>();
        DiffMappingBuilder diffMappingBuilder = new DiffMappingBuilderImpl(ComplexElement.class, metadatas);

        diffMappingBuilder.mapping("objectElement", "name");

        DiffMetadata diffMetadata = diffMappingBuilder.configuration().build().get(0);

        assertSame(DiffStrategyType.DEEP.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElement", diffMetadata.getMethod().getName());
    }

    @Test
    public void testMustMapFieldWithCollectionStrategy() {
        HashMap<String, DiffMetadata> metadatas = new HashMap<>();
        DiffMappingBuilder diffMappingBuilder = new DiffMappingBuilderImpl(ComplexElement.class, metadatas);

        diffMappingBuilder.mapping("objectElementList", "name");

        DiffMetadata diffMetadata = diffMappingBuilder.configuration().build().get(0);

        assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElementList", diffMetadata.getMethod().getName());
    }

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenMethodNotPublic() {
        DiffMappingBuilder diffMappingBuilder = new DiffMappingBuilderImpl(ComplexElement.class, new HashMap<String, DiffMetadata>());
        diffMappingBuilder.mapping("notAccessible");
    }

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenMethodNotNoArgs() {
        DiffMappingBuilder diffMappingBuilder = new DiffMappingBuilderImpl(ComplexElement.class, new HashMap<String, DiffMetadata>());
        diffMappingBuilder.mapping("withArgs");
    }

}