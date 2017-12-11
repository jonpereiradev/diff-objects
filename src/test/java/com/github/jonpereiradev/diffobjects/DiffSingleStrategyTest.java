package com.github.jonpereiradev.diffobjects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiffSingleStrategyTest {

    @Mock
    private Diff diffAnnotation;

    private DiffStrategable diffStrategy;

    @Before
    public void beforeTest() {
        diffStrategy = new DiffSingleStrategy();
    }

    @Test
    public void testSingleStrategyEquals() {
        String objectA = "Object A";
        String objectB = "Object A";
        DiffObject<String> diffObject = diffStrategy.diff(diffAnnotation, objectA, objectB);

        Assert.assertNull(diffObject);
    }

    @Test
    public void testSingleStrategyDifferent() {
        String objectA = "Object A";
        String objectB = "Object B";
        DiffObject<String> diffObject = diffStrategy.diff(diffAnnotation, objectA, objectB);

        Assert.assertNotNull(diffObject);
        Assert.assertEquals("Object A", diffObject.getBefore());
        Assert.assertEquals("Object B", diffObject.getAfter());
    }
}
