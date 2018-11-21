package com.github.jonpereiradev.diffobjects;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class DiffObjectsWithAnnotationsTest {

    private DiffObjects<ObjectElement> diffObjects;

    @Before
    public void beforeTest() {
        diffObjects = DiffObjects.forClass(ObjectElement.class);
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        diffObjects.diff(null, new ObjectElement(null));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullBeforeStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        diffObjects.diff(new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullAfterStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;

        diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        diffObjects.isEquals(null, new ObjectElement(null));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullBeforeStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;

        diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        diffObjects.isEquals(new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullAfterStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        List<DiffResult> results = diffObjects.diff(objectA, objectB);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertEquals("Object", results.get(0).getBefore());
        Assert.assertEquals("Object", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsCollectionWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement2 objectA = new ObjectElement2("Object", null);
        ObjectElement2 objectB = new ObjectElement2("Object 2", "parent2");
        ObjectElement2 objectC = new ObjectElement2("Object", null);
        ObjectElement2 objectD = new ObjectElement2("Object 2", "parent2");

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Collection<ObjectElement2> a = Arrays.asList(objectA, objectB);
        Collection<ObjectElement2> b = Arrays.asList(objectC, objectD);

        List<DiffResult> results = diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertTrue(results.get(1).isEquals());
    }

    @Test
    public void testDiffObjectsWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        List<DiffResult> results = diffObjects.diff(objectA, objectB);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertFalse(results.get(0).isEquals());
        Assert.assertEquals("Object A", results.get(0).getBefore());
        Assert.assertEquals("Object B", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement2 objectA = new ObjectElement2("Object", null);
        ObjectElement2 objectB = new ObjectElement2("Object 2", "parent1");
        ObjectElement2 objectC = new ObjectElement2("Object", null);
        ObjectElement2 objectD = new ObjectElement2("Object 2", "parent2");

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Collection<ObjectElement2> cA = Arrays.asList(objectA, objectB);
        Collection<ObjectElement2> cB = Arrays.asList(objectC, objectD);

        List<DiffResult> results = diffObjects.diff(cA, cB, (a, b) -> a.getName().equals(b.getName()));

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertFalse(results.get(1).isEquals());
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentNameMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        List<DiffResult> results = diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assert.assertFalse(results.get(0).isEquals());
        Assert.assertFalse(results.get(1).isEquals());
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assert.assertTrue(diffObjects.isEquals(objectA, objectB));
    }

    @Test
    public void testDiffObjectsCollectionWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        Assert.assertTrue(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assert.assertFalse(diffObjects.isEquals(objectA, objectB));
    }

    @Test
    public void testDiffObjectsCollectionWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement2 objectA = new ObjectElement2("Object A", "name1");
        ObjectElement2 objectB = new ObjectElement2("Object A", "name2");
        Collection<ObjectElement2> a = Collections.singletonList(objectA);
        Collection<ObjectElement2> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Assert.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentObjectElementAMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        Assert.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentObjectElementBMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Arrays.asList(objectA, objectB);

        Assert.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }
}
