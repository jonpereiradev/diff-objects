package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DiffSingleStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @BeforeEach
    void beforeEach() {
        diffStrategy = DiffStrategyType.SINGLE.getStrategy();
        diffMetadata = discoverByName(ObjectElement.class, "getName");
        Assertions.assertNotNull(diffStrategy);
        Assertions.assertNotNull(diffMetadata);
    }

    @Test
    void testSingleStrategyEqualsNullObjects() {
        DiffResult diffResult = diffStrategy.diff(null, null, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testSingleStrategyDifferentNullObjectA() {
        DiffResult diffResult = diffStrategy.diff(null, new ObjectElement("Object B"), diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNotNull(diffResult.getCurrent());
    }

    @Test
    void testSingleStrategyDifferentNullObjectB() {
        DiffResult diffResult = diffStrategy.diff(new ObjectElement("Object A"), null, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertNotNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testSingleStrategyEquals() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object A");
        DiffResult diffResult = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertEquals("Object A", diffResult.getExpected());
        Assertions.assertEquals("Object A", diffResult.getCurrent());
    }

    @Test
    void testSingleStrategyDifferent() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResult diffResult = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertEquals("Object A", diffResult.getExpected());
        Assertions.assertEquals("Object B", diffResult.getCurrent());
    }
}
