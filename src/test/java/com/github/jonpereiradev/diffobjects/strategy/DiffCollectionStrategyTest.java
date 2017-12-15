package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiffCollectionStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;

    @Before
    public void beforeTest() {
        diffStrategy = DiffStrategyType.COLLECTION.getStrategy();
        diffMetadata = discoverByName(ComplexElement.class,"getObjectElementList");
        Assert.assertNotNull(diffStrategy);
        Assert.assertNotNull(diffMetadata);
    }

    @Test
    public void testCollectionStrategyEqualsNullObjects() {
        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(null, null, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyEqualsObjectsNullLists() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyEqualsObjectsEmptyLists() {
        ComplexElement complexA = new ComplexElement(new ArrayList<ObjectElement>());
        ComplexElement complexB = new ComplexElement(new ArrayList<ObjectElement>());
        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsNullListA() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement(new ArrayList<ObjectElement>());
        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsNullListB() {
        ComplexElement complexA = new ComplexElement(new ArrayList<ObjectElement>());
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsDifferentSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<ObjectElement>());
        ComplexElement complexB = new ComplexElement(new ArrayList<ObjectElement>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.B"));

        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertTrue(diffResult.getBefore().size() == 1);
        Assert.assertEquals("Object A.A", diffResult.getBefore().get(0).getName());
        Assert.assertTrue(diffResult.getAfter().size() == 2);
        Assert.assertEquals("Object B.A", diffResult.getAfter().get(0).getName());
        Assert.assertEquals("Object B.B", diffResult.getAfter().get(1).getName());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<ObjectElement>());
        ComplexElement complexB = new ComplexElement(new ArrayList<ObjectElement>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));

        DiffResult<List<ObjectElement>> diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertTrue(diffResult.getBefore().size() == 1);
        Assert.assertEquals("Object A.A", diffResult.getBefore().get(0).getName());
        Assert.assertTrue(diffResult.getAfter().size() == 1);
        Assert.assertEquals("Object B.A", diffResult.getAfter().get(0).getName());
    }
}
