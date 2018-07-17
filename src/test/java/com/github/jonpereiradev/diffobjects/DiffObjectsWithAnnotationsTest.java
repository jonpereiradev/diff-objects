package com.github.jonpereiradev.diffobjects;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class DiffObjectsWithAnnotationsTest {

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        DiffObjects.diff(null, new ObjectElement(null));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        DiffObjects.diff(new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        DiffObjects.isEquals(null, new ObjectElement(null));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        DiffObjects.isEquals(new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        List<DiffResult> results = DiffObjects.diff(objectA, objectB);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertEquals("Object", results.get(0).getBefore());
        Assert.assertEquals("Object", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        List<DiffResult> results = DiffObjects.diff(objectA, objectB);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertFalse(results.get(0).isEquals());
        Assert.assertEquals("Object A", results.get(0).getBefore());
        Assert.assertEquals("Object B", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assert.assertTrue(DiffObjects.isEquals(objectA, objectB));
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assert.assertFalse(DiffObjects.isEquals(objectA, objectB));
    }
}
