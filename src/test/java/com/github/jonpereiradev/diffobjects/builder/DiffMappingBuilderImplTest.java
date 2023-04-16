package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class DiffMappingBuilderImplTest {

    @Test
    public void testMustMapFieldWithSingleStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("parent");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getParent", diffMetadata.getMethod().getName());
    }

    @Test
    public void testMustMapFieldWithDeepStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("objectElement.name");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.NESTED.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElement", diffMetadata.getMethod().getName());
    }

    @Test
    public void testMustMapFieldWithCollectionStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("objectElementList.name");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElementList", diffMetadata.getMethod().getName());
    }

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenMethodNotPublic() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);
        diffMappingBuilder.map("notAccessible");
    }

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenMethodNotNoArgs() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);
        diffMappingBuilder.map("withArgs");
    }

}