package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.builder.DiffConfigBuilder;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class DiffObjectsWithBuilderTest {

    private DiffObjects<ObjectElement> diffObjects;
    private DiffConfig configuration;

    @Before
    public void beforeTest() {
        diffObjects = DiffObjects.forClass(ObjectElement.class);
        configuration = DiffConfigBuilder.forClass(ObjectElement.class).mapping().fields().map("name").build();
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
        DiffResults results = diffObjects.diff(objectA, objectB, configuration);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());

        DiffResult result = results.stream().findFirst().orElseThrow(RuntimeException::new);

        Assert.assertTrue(result.isEquals());
        Assert.assertEquals("Object", result.getExpected());
        Assert.assertEquals("Object", result.getCurrent());
    }

    @Test
    public void testDiffObjectsCollectionWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement> diffObjects = DiffObjects.forClass(ObjectElement.class);
        DiffConfigBuilder<ObjectElement> diffBuilder = DiffConfigBuilder.forClass(ObjectElement.class);
        DiffConfig configuration = diffBuilder.mapping().fields().map("name").build();

        DiffResults results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));
        DiffResult result = results.stream().findFirst().orElse(null);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEquals());
    }

    @Test
    public void testDiffObjectsWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults results = diffObjects.diff(objectA, objectB, configuration);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());

        DiffResult result = results.stream().findFirst().orElseThrow(NullPointerException::new);

        Assert.assertFalse(result.isEquals());
        Assert.assertEquals("Object A", result.getExpected());
        Assert.assertEquals("Object B", result.getCurrent());
    }

    @Test
    public void testDiffObjectsCollectionWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement2 objectA = new ObjectElement2("Object", "name1");
        ObjectElement2 objectB = new ObjectElement2("Object", "name2");
        Collection<ObjectElement2> a = Collections.singletonList(objectA);
        Collection<ObjectElement2> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);
        DiffConfigBuilder<ObjectElement2> diffBuilder = DiffConfigBuilder.forClass(ObjectElement2.class);
        DiffConfig configuration = diffBuilder.mapping().fields().map("name").map("name2").build();

        DiffResults results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));
        DiffResult result = results.stream().findFirst().orElse(null);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEquals());
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
}
