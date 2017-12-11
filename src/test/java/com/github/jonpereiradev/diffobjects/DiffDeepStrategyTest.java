package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(MockitoJUnitRunner.class)
public class DiffDeepStrategyTest {

    @Mock
    private Method method;

    private DiffStrategable diffStrategy;

    @Before
    public void beforeTest() {
        diffStrategy = new DiffDeepStrategy();
    }

    @Test
    public void testDeepStrategyEquals() throws InvocationTargetException, IllegalAccessException {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object A"));

        Mockito.when(method.invoke(objectA)).thenReturn(objectA.getObjectElement());
        Mockito.when(method.invoke(objectB)).thenReturn(objectB.getObjectElement());

        DiffObject<ComplexElement> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNull(diffObject);
    }

    @Test
    @Ignore
    public void testDeepStrategyDifferent() throws InvocationTargetException, IllegalAccessException {
        ComplexElement objectA = new ComplexElement(new ObjectElement("Object A"));
        ComplexElement objectB = new ComplexElement(new ObjectElement("Object B"));

        Mockito.when(method.invoke(objectA)).thenReturn(objectA.getObjectElement());
        Mockito.when(method.invoke(objectB)).thenReturn(objectB.getObjectElement());

        DiffObject<ComplexElement> diffObject = diffStrategy.diff(objectA, objectB, method);

        Assert.assertNotNull(diffObject);
        Assert.assertEquals("Object A", diffObject.getBefore().getObjectElement().getName());
        Assert.assertEquals("Object B", diffObject.getAfter().getObjectElement().getName());
    }

}
