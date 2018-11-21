package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.builder.DiffBuilder;
import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class DiffObjectsWithBuilderTest {

    private DiffObjects<ObjectElement> diffObjects;
    private DiffConfiguration configuration;

    @Before
    public void beforeTest() {
        diffObjects = DiffObjects.forClass(ObjectElement.class);
        configuration = DiffBuilder.map(ObjectElement.class).mapping("name").configuration();
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        diffObjects.diff(null, new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullBeforeStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        diffObjects.diff(new ObjectElement(null), null, null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullAfterStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;

        diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullConfigurationStateMustThrowNullPointerException() {
        diffObjects.diff(new ObjectElement(null), new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullConfigurationStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        diffObjects.isEquals(null, new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullBeforeStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        diffObjects.isEquals(new ObjectElement(null), null, null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullAfterStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;

        diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsWithNullConfigurationEqualsStateMustThrowNullPointerException() {
        diffObjects.isEquals(new ObjectElement(null), new ObjectElement(null), null);
        Assert.fail("Must throw NullPointerException");
    }

    @Test(expected = NullPointerException.class)
    public void testDiffObjectsCollectionWithNullConfigurationEqualsStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = new ArrayList<>();

        diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName()));
        Assert.fail("Must throw NullPointerException");
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        List<DiffResult> results = diffObjects.diff(objectA, objectB, configuration);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertEquals("Object", results.get(0).getBefore());
        Assert.assertEquals("Object", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsCollectionWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement> diffObjects = DiffObjects.forClass(ObjectElement.class);
        DiffBuilder diffBuilder = DiffBuilder.map(ObjectElement.class);
        DiffConfiguration configuration = diffBuilder.mapping("name").configuration();

        List<DiffResult> results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assert.assertFalse(results.isEmpty());
        Assert.assertTrue(results.get(0).isEquals());
    }

    @Test
    public void testDiffObjectsWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        List<DiffResult> results = diffObjects.diff(objectA, objectB, configuration);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertFalse(results.get(0).isEquals());
        Assert.assertEquals("Object A", results.get(0).getBefore());
        Assert.assertEquals("Object B", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement2 objectA = new ObjectElement2("Object", "name1");
        ObjectElement2 objectB = new ObjectElement2("Object", "name2");
        Collection<ObjectElement2> a = Collections.singletonList(objectA);
        Collection<ObjectElement2> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);
        DiffBuilder diffBuilder = DiffBuilder.map(ObjectElement2.class);
        DiffConfiguration configuration = diffBuilder.mapping("name").mapping("name2", String.class).configuration();

        List<DiffResult> results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assert.assertFalse(results.isEmpty());
        Assert.assertFalse(results.get(0).isEquals());
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assert.assertTrue(diffObjects.isEquals(objectA, objectB, configuration));
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assert.assertFalse(diffObjects.isEquals(objectA, objectB, configuration));
    }

    @Test
    public void testDiffObjectsWithEqualsComplexElementMustReturnEquals() {
        DiffConfiguration configuration = DiffBuilder
            .map(ComplexElement.class)
            .mappingCollection("objectElementList", ObjectElement.class, (o1, o2) -> o1.getName().equals(o2.getName()))
            .mapping("name")
            .mapping("parent")
            .mappingBuilder()
            .mapping("objectElement")
            .configuration();

        System.out.println(configuration.build().toString());
    }
}
