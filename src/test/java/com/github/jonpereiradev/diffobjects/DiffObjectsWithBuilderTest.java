package com.github.jonpereiradev.diffobjects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class DiffObjectsWithBuilderTest {

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        DiffObjects.diff(null, new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        DiffObjects.diff(new ObjectElement(null), null, null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullConfigurationStateMustThrowNullPointerException() {
        DiffObjects.diff(new ObjectElement(null), new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        DiffObjects.isEquals(null, new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        DiffObjects.isEquals(new ObjectElement(null), null, null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullConfigurationEqualsStateMustThrowNullPointerException() {
        DiffObjects.isEquals(new ObjectElement(null), new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test
    @Ignore
    public void testDiffObjectsWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        List<DiffResult<?>> results = DiffObjects.diff(objectA, objectB, null);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertEquals("Object", results.get(0).getBefore());
        Assert.assertEquals("Object", results.get(0).getAfter());
    }

    @Test
    @Ignore
    public void testDiffObjectsWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        List<DiffResult<?>> results = DiffObjects.diff(objectA, objectB, null);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertFalse(results.get(0).isEquals());
        Assert.assertEquals("Object A", results.get(0).getBefore());
        Assert.assertEquals("Object B", results.get(0).getAfter());
    }

    @Test
    @Ignore
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assert.assertTrue(DiffObjects.isEquals(objectA, objectB, null));
    }

    @Test
    @Ignore
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assert.assertFalse(DiffObjects.isEquals(objectA, objectB, null));
    }
}
