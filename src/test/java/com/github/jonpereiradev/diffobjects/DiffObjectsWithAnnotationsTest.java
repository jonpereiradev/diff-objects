package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;


class DiffObjectsWithAnnotationsTest {

    private DiffObjects<ObjectElement> diffObjects;

    @BeforeEach
    void beforeEach() {
        diffObjects = DiffObjects.forClass(ObjectElement.class);
    }

    @Test
    void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.diff(null, new ObjectElement(null)));
    }

    @Test
    void testDiffObjectsCollectionWithNullBeforeStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.diff(new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullAfterStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;
        assertThrows(NullPointerException.class, () -> diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(null, new ObjectElement(null)));
    }

    @Test
    void testDiffObjectsCollectionWithNullBeforeStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullAfterStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        DiffResults results = diffObjects.diff(objectA, objectB);

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());
        Assertions.assertTrue(results.getResults().get(0).isEquals());
        Assertions.assertEquals("Object", results.getResults().get(0).getExpected());
        Assertions.assertEquals("Object", results.getResults().get(0).getCurrent());
    }

    @Test
    void testDiffObjectsCollectionWithEqualsObjectElementMustReturnDiffWithEquals() {
        ObjectElement2 objectA = new ObjectElement2("Object", null);
        ObjectElement2 objectB = new ObjectElement2("Object 2", "parent2");
        ObjectElement2 objectC = new ObjectElement2("Object", null);
        ObjectElement2 objectD = new ObjectElement2("Object 2", "parent2");

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Collection<ObjectElement2> a = Arrays.asList(objectA, objectB);
        Collection<ObjectElement2> b = Arrays.asList(objectC, objectD);

        DiffResults results = diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(2, results.size());
        Assertions.assertTrue(results.getResults().get(0).isEquals());
        Assertions.assertTrue(results.getResults().get(1).isEquals());
    }

    @Test
    void testDiffObjectsWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults results = diffObjects.diff(objectA, objectB);

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());
        Assertions.assertFalse(results.getResults().get(0).isEquals());
        Assertions.assertEquals("Object A", results.getResults().get(0).getExpected());
        Assertions.assertEquals("Object B", results.getResults().get(0).getCurrent());
    }

    @Test
    void testDiffObjectsCollectionWithDifferentObjectElementMustReturnDiffWithDifference() {
        ObjectElement2 objectA = new ObjectElement2("Object", null);
        ObjectElement2 objectB = new ObjectElement2("Object 2", "parent1");
        ObjectElement2 objectC = new ObjectElement2("Object", null);
        ObjectElement2 objectD = new ObjectElement2("Object 2", "parent2");

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Collection<ObjectElement2> cA = Arrays.asList(objectA, objectB);
        Collection<ObjectElement2> cB = Arrays.asList(objectC, objectD);

        DiffResults results = diffObjects.diff(cA, cB, (a, b) -> a.getName().equals(b.getName()));

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(2, results.size());
        Assertions.assertTrue(results.getResults().get(0).isEquals());
        Assertions.assertFalse(results.getResults().get(1).isEquals());
    }

    @Test
    void testDiffObjectsCollectionWithDifferentNameMustReturnDiffWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        DiffResults results = diffObjects.diff(a, b, (o1, o2) -> o1.getName().equals(o2.getName()));

        Assertions.assertFalse(results.getResults().get(0).isEquals());
        Assertions.assertFalse(results.getResults().get(1).isEquals());
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assertions.assertTrue(diffObjects.isEquals(objectA, objectB));
    }

    @Test
    void testDiffObjectsCollectionWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        Assertions.assertTrue(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assertions.assertFalse(diffObjects.isEquals(objectA, objectB));
    }

    @Test
    void testDiffObjectsCollectionWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement2 objectA = new ObjectElement2("Object A", "name1");
        ObjectElement2 objectB = new ObjectElement2("Object A", "name2");
        Collection<ObjectElement2> a = Collections.singletonList(objectA);
        Collection<ObjectElement2> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);

        Assertions.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsCollectionWithDifferentObjectElementAMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        Assertions.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsCollectionWithDifferentObjectElementBMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Arrays.asList(objectA, objectB);

        Assertions.assertFalse(diffObjects.isEquals(a, b, (o1, o2) -> o1.getName().equals(o2.getName())));
    }
}
