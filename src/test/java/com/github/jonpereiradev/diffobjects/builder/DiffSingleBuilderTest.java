package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiffSingleBuilderTest {

    private DiffConfigurationBuilder objectElementConfiguration;

    @Before
    public void beforeTest() {
        objectElementConfiguration = DiffBuilder.map(ObjectElement.class)
            .mapper()
            .mapping("name")
            .builder()
            .getConfiguration();
    }

    @Test
    public void testDiffBuilderEqualsObjectElementNulls() {
        DiffResults diffResults = DiffObjects.diff(null, null, objectElementConfiguration);
        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
        Assert.assertNull(diffResults.getResults().get(0).getBefore());
        Assert.assertNull(diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderEqualsObjectElementNamesNull() {
        ObjectElement objectA = new ObjectElement(null);
        ObjectElement objectB = new ObjectElement(null);
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, objectElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
        Assert.assertNull(diffResults.getResults().get(0).getBefore());
        Assert.assertNull(diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderEqualsObjectElementNamesEquals() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object A");
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, objectElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getBefore());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderDifferentObjectElementA() {
        ObjectElement objectA = new ObjectElement("Object A");
        DiffResults diffResults = DiffObjects.diff(objectA, null, objectElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getBefore());
        Assert.assertNull(diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderDifferentObjectElementB() {
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults diffResults = DiffObjects.diff(null, objectB, objectElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
        Assert.assertNull(diffResults.getResults().get(0).getBefore());
        Assert.assertEquals("Object B", diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderDifferentObjectElementNames() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, objectElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getBefore());
        Assert.assertEquals("Object B", diffResults.getResults().get(0).getAfter());
    }

}
