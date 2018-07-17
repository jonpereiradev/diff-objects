package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DiffSingleStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @Before
    public void beforeTest() {
        diffStrategy = DiffStrategyType.SINGLE.getStrategy();
        diffMetadata = discoverByName(ObjectElement.class, "getName");
        Assert.assertNotNull(diffStrategy);
        Assert.assertNotNull(diffMetadata);
    }

    @Test
    public void testSingleStrategyEqualsNullObjects() {
        DiffResult diffResult = diffStrategy.diff(null, null, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testSingleStrategyDifferentNullObjectA() {
        DiffResult diffResult = diffStrategy.diff(null, new ObjectElement("Object B"), diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
    }

    @Test
    public void testSingleStrategyDifferentNullObjectB() {
        DiffResult diffResult = diffStrategy.diff(new ObjectElement("Object A"), null, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testSingleStrategyEquals() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object A");
        DiffResult diffResult = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertEquals("Object A", diffResult.getBefore());
        Assert.assertEquals("Object A", diffResult.getAfter());
    }

    @Test
    public void testSingleStrategyDifferent() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResult diffResult = diffStrategy.diff(objectA, objectB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertEquals("Object A", diffResult.getBefore());
        Assert.assertEquals("Object B", diffResult.getAfter());
    }
}
