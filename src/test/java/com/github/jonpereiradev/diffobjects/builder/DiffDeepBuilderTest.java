package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiffDeepBuilderTest {

    private DiffConfigurationBuilder complexElementConfiguration;

    @Before
    public void beforeTest() {
        complexElementConfiguration = DiffBuilder.map(ComplexElement.class)
            .mapper()
            .mapping("objectElement", "name")
            .mapping("objectElementList")
            .builder()
            .getConfiguration();
    }

    @Test
    public void testDiffBuilderEqualsComplexElementObjectsNull() {
        ComplexElement objectA = new ComplexElement((ObjectElement) null);
        ComplexElement objectB = new ComplexElement((ObjectElement) null);
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, complexElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
        Assert.assertNull(diffResults.getResults().get(0).getBefore());
        Assert.assertNull(diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderEqualsComplexElementObjectsEquals() {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object A"));
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, complexElementConfiguration);

        objectA = (ComplexElement) diffResults.getResults().get(0).getBefore();
        objectB = (ComplexElement) diffResults.getResults().get(0).getAfter();

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", objectA.getObjectElement().getName());
        Assert.assertEquals("Object A", objectB.getObjectElement().getName());
    }

    @Test
    public void testDiffBuilderDifferentObjectElementA() {
        ObjectElement objectA = new ObjectElement("Object A");
        DiffResults diffResults = DiffObjects.diff(objectA, null, complexElementConfiguration);
        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getBefore());
        Assert.assertNull(diffResults.getResults().get(0).getAfter());
    }

    @Test
    public void testDiffBuilderDifferentObjectElementB() {
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults diffResults = DiffObjects.diff(null, objectB, complexElementConfiguration);

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
        DiffResults diffResults = DiffObjects.diff(objectA, objectB, complexElementConfiguration);

        Assert.assertFalse(diffResults.getResults().isEmpty());
        Assert.assertEquals(1, diffResults.getResults().size());
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
        Assert.assertEquals("Object A", diffResults.getResults().get(0).getBefore());
        Assert.assertEquals("Object B", diffResults.getResults().get(0).getAfter());
    }

}
