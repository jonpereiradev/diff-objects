package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiffBuilderTest {

    private DiffConfiguration diffConfiguration;

    @Before
    public void beforeTest() {
        diffConfiguration = DiffBuilder.map(ObjectElement.class)
            .mapper()
            .mapping("name")
            .builder()
            .getConfiguration();
    }

    @Test
    public void testDiffBuilderEqualsObjectsElementNulls() {
        DiffResults diffResults = DiffObjects.diff(null, null, diffConfiguration);
        Assert.assertTrue(diffResults.getResults().get(0).isEquals());
    }

    @Test
    public void testDiffBuilderDifferentObjectB() {
        DiffResults diffResults = DiffObjects.diff(null, new ObjectElement("Object B"), diffConfiguration);
        Assert.assertFalse(diffResults.getResults().get(0).isEquals());
    }

}
