package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class DiffDeepStrategyTest {

    private DiffStrategy diffStrategy;
    private Method method;

    @Before
    public void beforeTest() throws NoSuchMethodException {
        diffStrategy = DiffStrategyType.DEEP.getStrategy();
        method = ComplexElement.class.getMethod("getObjectElement");
    }

    @Test
    public void testDeepStrategyEquals() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object A"));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertTrue(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertEquals("Object A", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferent() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentComplexBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, null, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectBNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectBValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement(null));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentComplexANull() {
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult<String> diffObject = diffStrategy.diff(null, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjecANull() {
        ComplexElement objectA = new ComplexElement((ObjectElement) null);
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyDifferentObjectAValueNull() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertFalse(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }

    @Test
    public void testDeepStrategyEqualsObjectStructureNullValues() {
        ComplexElement objectA = new ComplexElement(new ObjectElement(null));
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResult<String> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertTrue(diffObject.isEquals());
        Assert.assertNull(diffObject.getBefore());
        Assert.assertNull(diffObject.getAfter());
    }

}
