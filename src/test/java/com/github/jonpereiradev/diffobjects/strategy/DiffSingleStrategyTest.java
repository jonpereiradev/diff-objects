package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class DiffSingleStrategyTest {

    private DiffStrategy diffStrategy;
    private Method method;

    @Before
    public void beforeTest() throws NoSuchMethodException {
        diffStrategy = DiffStrategyType.SINGLE.getStrategy();
        method = ObjectElement.class.getMethod("getName");
    }

    @Test
    public void testSingleStrategyEquals() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object A");
        DiffResult<String> diffResult = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertEquals("Object A", diffResult.getBefore());
        Assert.assertEquals("Object A", diffResult.getAfter());
    }

    @Test
    public void testSingleStrategyDifferent() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResult<String> diffResult = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertEquals("Object A", diffResult.getBefore());
        Assert.assertEquals("Object B", diffResult.getAfter());
    }
}
