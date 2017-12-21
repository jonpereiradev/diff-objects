package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.builder.DiffBuilder;
import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DiffObjectsWithBuilderTest {

    private DiffConfiguration configuration;

    @Before
    public void beforeTest() {
        configuration = DiffBuilder.map(ObjectElement.class).mapper().mapping("name").instance().configuration();
    }

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
    public void testDiffObjectsWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        List<DiffResult<?>> results = DiffObjects.diff(objectA, objectB, configuration);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEquals());
        Assert.assertEquals("Object", results.get(0).getBefore());
        Assert.assertEquals("Object", results.get(0).getAfter());
    }

    @Test
    public void testDiffObjectsWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        List<DiffResult<?>> results = DiffObjects.diff(objectA, objectB, configuration);

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

        Assert.assertTrue(DiffObjects.isEquals(objectA, objectB, configuration));
    }

    @Test
    public void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assert.assertFalse(DiffObjects.isEquals(objectA, objectB, configuration));
    }
}
