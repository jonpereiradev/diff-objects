package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.ComplexElement;
import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.ObjectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DiffCollectionStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;
    private DiffMetadata diffMetadataByName;

    @Before
    public void beforeTest() {
        diffStrategy = DiffStrategyType.COLLECTION.getStrategy();
        diffMetadata = discoverByName(ComplexElement.class, "getObjectElementList");
        diffMetadataByName = discoverByName(ComplexElement.class, "getObjectElementListByName");
        Assert.assertNotNull(diffStrategy);
        Assert.assertNotNull(diffMetadata);
        Assert.assertNotNull(diffMetadataByName);
    }

    @Test
    public void testCollectionStrategyEqualsNullObjects() {
        DiffResult diffResult = diffStrategy.diff(null, null, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyEqualsObjectsNullLists() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyEqualsObjectsEmptyLists() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsNullListA() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    public void testCollectionStrategyDifferentObjectsNullListB() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNull(diffResult.getBefore());
        Assert.assertNull(diffResult.getAfter());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionStrategyDifferentObjectsDifferentSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.B"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getBefore();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getAfter();

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertNotNull(beforeCollection);
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertEquals(1, beforeCollection.size());
        Assert.assertEquals("Object A.A", beforeCollection.get(0).getName());
        Assert.assertEquals(2, afterCollection.size());
        Assert.assertEquals("Object B.A", afterCollection.get(0).getName());
        Assert.assertEquals("Object B.B", afterCollection.get(1).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionStrategyDifferentObjectsSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getBefore();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getAfter();

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertEquals(1, beforeCollection.size());
        Assert.assertEquals("Object A.A", beforeCollection.get(0).getName());
        Assert.assertEquals(1, afterCollection.size());
        Assert.assertEquals("Object B.A", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionStrategySameObjectsSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        ObjectElement objectElement = new ObjectElement("Object");

        complexA.getObjectElementList().add(objectElement);
        complexB.getObjectElementList().add(objectElement);

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getBefore();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getAfter();

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertEquals(1, beforeCollection.size());
        Assert.assertEquals("Object", beforeCollection.get(0).getName());
        Assert.assertEquals(1, afterCollection.size());
        Assert.assertEquals("Object", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionStrategySameObjectsByNameSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementListByName().add(new ObjectElement("Object"));
        complexB.getObjectElementListByName().add(new ObjectElement("Object"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadataByName);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getBefore();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getAfter();

        Assert.assertNotNull(diffResult);
        Assert.assertTrue(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertEquals(1, beforeCollection.size());
        Assert.assertEquals("Object", beforeCollection.get(0).getName());
        Assert.assertEquals(1, afterCollection.size());
        Assert.assertEquals("Object", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionStrategyDifferentObjectsByNameSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementListByName().add(new ObjectElement("Object A"));
        complexB.getObjectElementListByName().add(new ObjectElement("Object B"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadataByName);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getBefore();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getAfter();

        Assert.assertNotNull(diffResult);
        Assert.assertFalse(diffResult.isEquals());
        Assert.assertNotNull(diffResult.getBefore());
        Assert.assertNotNull(diffResult.getAfter());
        Assert.assertEquals(1, beforeCollection.size());
        Assert.assertEquals("Object A", beforeCollection.get(0).getName());
        Assert.assertEquals(1, afterCollection.size());
        Assert.assertEquals("Object B", afterCollection.get(0).getName());
    }
}
