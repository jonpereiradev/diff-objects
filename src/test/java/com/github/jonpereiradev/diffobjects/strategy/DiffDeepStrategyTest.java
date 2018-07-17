package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DiffDeepStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @Before
    public void beforeTest() {
        diffStrategy = DiffStrategyType.DEEP.getStrategy();
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
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertEquals("Object A", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferent() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentComplexBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        DiffResult diffObject = diffStrategy.diff(objectA, null, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectBValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement(null));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentComplexANull() {
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(null, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjecANull() {
        ComplexElement objectA = new ComplexElement((ObjectElement) null);
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectAValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyEqualsObjectStructureNullValues() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult diffObject = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffObject);
        Assert.assertTrue(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

}
