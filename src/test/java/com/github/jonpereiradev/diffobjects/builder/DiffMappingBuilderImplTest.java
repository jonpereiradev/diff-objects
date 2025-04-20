package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.comparator.EqualsComparator;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffStrategyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiffMappingBuilderImplTest {

    @Test
    void testMustMapFieldWithSingleStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("parent");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.SINGLE.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getParent", diffMetadata.getMethod().getName());
    }

    @Test
    void testMustMapFieldWithDeepStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("objectElement.name");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.NESTED.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElement", diffMetadata.getMethod().getName());
    }

    @Test
    void testMustMapFieldWithCollectionStrategy() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);

        diffMappingBuilder.map("objectElementList.name");

        DiffMetadata diffMetadata = diffMappingBuilder.build().build().get(0);

        assertSame(DiffStrategyType.COLLECTION.getStrategy(), diffMetadata.getStrategy());
        assertEquals(EqualsComparator.class, diffMetadata.getComparator().getClass());
        assertEquals("getObjectElementList", diffMetadata.getMethod().getName());
    }

    @Test
    void testMustThrowExceptionWhenMethodNotPublic() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);
        assertThrows(DiffException.class, () -> diffMappingBuilder.map("notAccessible"));
    }

    @Test
    void testMustThrowExceptionWhenMethodNotNoArgs() {
        DiffBuilderContext<ComplexElement> context = new DiffBuilderContext<>(ComplexElement.class);
        DiffManualMappingBuilder<?> diffMappingBuilder = new DiffMappingFieldBuilderImpl<>(context);
        assertThrows(DiffException.class, () -> diffMappingBuilder.map("withArgs"));
    }

}