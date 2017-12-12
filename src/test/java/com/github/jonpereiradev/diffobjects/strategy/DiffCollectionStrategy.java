package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

public class DiffCollectionStrategy {

    private DiffStrategy diffStrategy;
    private Method method;

    @Before
    public void beforeTest() throws NoSuchMethodException {
        diffStrategy = DiffStrategyType.COLLECTION.getStrategy();
        method = ComplexElement.class.getMethod("getObjectElementList");
    }

    @Test
    public void testCollectionStrategyEqualsNullObjects() {
        DiffResult<List<String>> diffResult = diffStrategy.diff(null, null, method);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
    }
}
