package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DiffNestedStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @Before
    public void beforeTest() {
        diffStrategy = DiffStrategyType.NESTED.getStrategy();
        diffMetadata = discoverByName(ComplexElement.class, "getObjectElement");
        Assert.assertNotNull(diffStrategy);
        Assert.assertNotNull(diffMetadata);
    }

    @Test
    public void testDeepStrategyEquals() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object A"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertTrue(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getExpected());
        Assert.assertEquals("Object A", diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferent() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getExpected());
        Assert.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentComplexBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        DiffResult diffObject = diffStrategy.diff(objectA, null, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getExpected());
        Assert.assertNull(diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentObjectBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getExpected());
        Assert.assertNull(diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentObjectBValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement(null));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getExpected());
        Assert.assertNull(diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentComplexANull() {
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(null, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getExpected());
        Assert.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentObjecANull() {
        ComplexElement objectA = new ComplexElement((ObjectElement) null);
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getExpected());
        Assert.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyDifferentObjectAValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getExpected());
        Assert.assertEquals("Object B", diffObject.getCurrent());
    }

    @Test
    public void testDeepStrategyEqualsObjectStructureNullValues() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertTrue(diffObject.isEquals());
        Assert.assertNull(diffObject.getExpected());
        Assert.assertNull(diffObject.getCurrent());
    }

}
