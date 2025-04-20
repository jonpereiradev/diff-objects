package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.builder.DiffConfigBuilder;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import com.github.jonpereiradev.diffobjects.model.ObjectElement2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;


class DiffObjectsWithBuilderTest {

    private DiffObjects<ObjectElement> diffObjects;
    private DiffConfig configuration;

    @BeforeEach
    void beforeEach() {
        diffObjects = DiffObjects.forClass(ObjectElement.class);
        configuration = DiffConfigBuilder.forClass(ObjectElement.class).mapping().fields().map("name").build();
    }

    @Test
    void testDiffObjectsWithNullBeforeStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.diff(null, new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullBeforeStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullAfterStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.diff(new ObjectElement(null), null, null));
    }

    @Test
    void testDiffObjectsCollectionWithNullAfterStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;
        assertThrows(NullPointerException.class, () -> diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullConfigurationStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.diff(new ObjectElement(null), new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullConfigurationStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.diff(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullBeforeStateEqualsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(null, new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullBeforeStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = null;
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullAfterStateEqualsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(new ObjectElement(null), null, null));
    }

    @Test
    void testDiffObjectsCollectionWithNullAfterStateEqualsMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = null;
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithNullConfigurationEqualsStateMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(new ObjectElement(null), new ObjectElement(null), null));
    }

    @Test
    void testDiffObjectsCollectionWithNullConfigurationEqualsStateMustThrowNullPointerException() {
        Collection<ObjectElement> a = new ArrayList<>();
        Collection<ObjectElement> b = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> diffObjects.isEquals(a, b, null, (o1, o2) -> o1.getName().equals(o2.getName())));
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        DiffResults results = diffObjects.diff(objectA, objectB, configuration);

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());

        DiffResult result = results.stream().findFirst().orElseThrow(RuntimeException::new);

        Assertions.assertTrue(result.isEquals());
        Assertions.assertEquals("Object", result.getExpected());
        Assertions.assertEquals("Object", result.getCurrent());
    }

    @Test
    void testDiffObjectsCollectionWithEqualsObjectElementMustReturnResultWithEquals() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");
        Collection<ObjectElement> a = Collections.singletonList(objectA);
        Collection<ObjectElement> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement> diffObjects = DiffObjects.forClass(ObjectElement.class);
        DiffConfigBuilder<ObjectElement> diffBuilder = DiffConfigBuilder.forClass(ObjectElement.class);
        DiffConfig configuration = diffBuilder.mapping().fields().map("name").build();

        DiffResults results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));
        DiffResult result = results.stream().findFirst().orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEquals());
    }

    @Test
    void testDiffObjectsWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");
        DiffResults results = diffObjects.diff(objectA, objectB, configuration);

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());

        DiffResult result = results.stream().findFirst().orElseThrow(NullPointerException::new);

        Assertions.assertFalse(result.isEquals());
        Assertions.assertEquals("Object A", result.getExpected());
        Assertions.assertEquals("Object B", result.getCurrent());
    }

    @Test
    void testDiffObjectsCollectionWithDifferentObjectElementMustReturnResultWithDifference() {
        ObjectElement2 objectA = new ObjectElement2("Object", "name1");
        ObjectElement2 objectB = new ObjectElement2("Object", "name2");
        Collection<ObjectElement2> a = Collections.singletonList(objectA);
        Collection<ObjectElement2> b = Collections.singletonList(objectB);

        DiffObjects<ObjectElement2> diffObjects = DiffObjects.forClass(ObjectElement2.class);
        DiffConfigBuilder<ObjectElement2> diffBuilder = DiffConfigBuilder.forClass(ObjectElement2.class);
        DiffConfig configuration = diffBuilder.mapping().fields().map("name").map("name2").build();

        DiffResults results = diffObjects.diff(a, b, configuration, (o1, o2) -> o1.getName().equals(o2.getName()));
        DiffResult result = results.stream().findFirst().orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEquals());
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnEqualsTrue() {
        ObjectElement objectA = new ObjectElement("Object");
        ObjectElement objectB = new ObjectElement("Object");

        Assertions.assertTrue(diffObjects.isEquals(objectA, objectB, configuration));
    }

    @Test
    void testDiffObjectsWithEqualsObjectElementMustReturnEqualsFalse() {
        ObjectElement objectA = new ObjectElement("Object A");
        ObjectElement objectB = new ObjectElement("Object B");

        Assertions.assertFalse(diffObjects.isEquals(objectA, objectB, configuration));
    }
}
