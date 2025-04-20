package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DiffNestedStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @BeforeEach
    void beforeEach() {
        diffStrategy = DiffStrategyType.NESTED.getStrategy();
        diffMetadata = discoverByName(ComplexElement.class, "getObjectElement");
        Assertions.assertNotNull(diffStrategy);
        Assertions.assertNotNull(diffMetadata);
    }

    @Test
    void testDeepStrategyEquals() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object A"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertTrue(diffObject.isEquals());
        Assertions.assertEquals("Object A", diffObject.getExpected());
        Assertions.assertEquals("Object A", diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferent() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertEquals("Object A", diffObject.getExpected());
        Assertions.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentComplexBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        DiffResult diffObject = diffStrategy.diff(objectA, null, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertEquals("Object A", diffObject.getExpected());
        Assertions.assertNull(diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentObjectBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertEquals("Object A", diffObject.getExpected());
        Assertions.assertNull(diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentObjectBValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement(null));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertEquals("Object A", diffObject.getExpected());
        Assertions.assertNull(diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentComplexANull() {
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(null, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertNull(diffObject.getExpected());
        Assertions.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentObjecANull() {
        ComplexElement objectA = new ComplexElement((ObjectElement) null);
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertNull(diffObject.getExpected());
        Assertions.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyDifferentObjectAValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertFalse(diffObject.isEquals());
        Assertions.assertNull(diffObject.getExpected());
        Assertions.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    void testDeepStrategyEqualsObjectStructureNullValues() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assertions.assertNotNull(diffObject);
        Assertions.assertTrue(diffObject.isEquals());
        Assertions.assertNull(diffObject.getExpected());
        Assertions.assertNull(diffObject.getCurrent());
    }

}
