package com.github.jonpereiradev.diffobjects.strategy;


import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.model.ComplexElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class DiffCollectionStrategyTest extends BaseStrategyTest {

    private DiffStrategy diffStrategy;
    private DiffMetadata diffMetadata;
    private DiffMetadata diffMetadataByName;

    @BeforeEach
    void beforeEach() {
        diffStrategy = DiffStrategyType.COLLECTION.getStrategy();
        diffMetadata = discoverByName(ComplexElement.class, "getObjectElementList");
        diffMetadataByName = discoverByName(ComplexElement.class, "getObjectElementListByName");
        Assertions.assertNotNull(diffStrategy);
        Assertions.assertNotNull(diffMetadata);
        Assertions.assertNotNull(diffMetadataByName);
    }

    @Test
    void testCollectionStrategyEqualsNullObjects() {
        DiffResult diffResult = diffStrategy.diff(null, null, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testCollectionStrategyEqualsObjectsNullLists() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testCollectionStrategyEqualsObjectsEmptyLists() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testCollectionStrategyDifferentObjectsNullListA() {
        ComplexElement complexA = new ComplexElement((List<ObjectElement>) null);
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    void testCollectionStrategyDifferentObjectsNullListB() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement((List<ObjectElement>) null);
        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNull(diffResult.getExpected());
        Assertions.assertNull(diffResult.getCurrent());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCollectionStrategyDifferentObjectsDifferentSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.B"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getExpected();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getCurrent();

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertNotNull(beforeCollection);
        Assertions.assertNotNull(diffResult.getCurrent());
        Assertions.assertEquals(1, beforeCollection.size());
        Assertions.assertEquals("Object A.A", beforeCollection.get(0).getName());
        Assertions.assertEquals(2, afterCollection.size());
        Assertions.assertEquals("Object B.A", afterCollection.get(0).getName());
        Assertions.assertEquals("Object B.B", afterCollection.get(1).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCollectionStrategyDifferentObjectsSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementList().add(new ObjectElement("Object A.A"));
        complexB.getObjectElementList().add(new ObjectElement("Object B.A"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getExpected();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getCurrent();

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertNotNull(diffResult.getExpected());
        Assertions.assertNotNull(diffResult.getCurrent());
        Assertions.assertEquals(1, beforeCollection.size());
        Assertions.assertEquals("Object A.A", beforeCollection.get(0).getName());
        Assertions.assertEquals(1, afterCollection.size());
        Assertions.assertEquals("Object B.A", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCollectionStrategySameObjectsSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());
        ObjectElement objectElement = new ObjectElement("Object");

        complexA.getObjectElementList().add(objectElement);
        complexB.getObjectElementList().add(objectElement);

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadata);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getExpected();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getCurrent();

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNotNull(diffResult.getExpected());
        Assertions.assertNotNull(diffResult.getCurrent());
        Assertions.assertEquals(1, beforeCollection.size());
        Assertions.assertEquals("Object", beforeCollection.get(0).getName());
        Assertions.assertEquals(1, afterCollection.size());
        Assertions.assertEquals("Object", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCollectionStrategySameObjectsByNameSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementListByName().add(new ObjectElement("Object"));
        complexB.getObjectElementListByName().add(new ObjectElement("Object"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadataByName);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getExpected();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getCurrent();

        Assertions.assertNotNull(diffResult);
        Assertions.assertTrue(diffResult.isEquals());
        Assertions.assertNotNull(diffResult.getExpected());
        Assertions.assertNotNull(diffResult.getCurrent());
        Assertions.assertEquals(1, beforeCollection.size());
        Assertions.assertEquals("Object", beforeCollection.get(0).getName());
        Assertions.assertEquals(1, afterCollection.size());
        Assertions.assertEquals("Object", afterCollection.get(0).getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCollectionStrategyDifferentObjectsByNameSameSizeList() {
        ComplexElement complexA = new ComplexElement(new ArrayList<>());
        ComplexElement complexB = new ComplexElement(new ArrayList<>());

        complexA.getObjectElementListByName().add(new ObjectElement("Object A"));
        complexB.getObjectElementListByName().add(new ObjectElement("Object B"));

        DiffResult diffResult = diffStrategy.diff(complexA, complexB, diffMetadataByName);
        List<ObjectElement> beforeCollection = (List<ObjectElement>) diffResult.getExpected();
        List<ObjectElement> afterCollection = (List<ObjectElement>) diffResult.getCurrent();

        Assertions.assertNotNull(diffResult);
        Assertions.assertFalse(diffResult.isEquals());
        Assertions.assertNotNull(diffResult.getExpected());
        Assertions.assertNotNull(diffResult.getCurrent());
        Assertions.assertEquals(1, beforeCollection.size());
        Assertions.assertEquals("Object A", beforeCollection.get(0).getName());
        Assertions.assertEquals(1, afterCollection.size());
        Assertions.assertEquals("Object B", afterCollection.get(0).getName());
    }
}
