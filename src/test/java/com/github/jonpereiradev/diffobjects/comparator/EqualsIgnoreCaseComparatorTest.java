package com.github.jonpereiradev.diffobjects.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EqualsIgnoreCaseComparatorTest {

    @Test
    void testIsEqualsWithBothNullMustReturnTrue() {
        EqualsIgnoreCaseComparator comparator = new EqualsIgnoreCaseComparator();
        Assertions.assertTrue(comparator.isEquals(null, null));
    }

    @Test
    void testIsEqualsWithExpectedNullMustReturnFalse() {
        EqualsIgnoreCaseComparator comparator = new EqualsIgnoreCaseComparator();
        Assertions.assertFalse(comparator.isEquals(null, "value"));
    }

    @Test
    void testIsEqualsWithCurrentNullMustReturnFalse() {
        EqualsIgnoreCaseComparator comparator = new EqualsIgnoreCaseComparator();
        Assertions.assertFalse(comparator.isEquals("value", null));
    }

    @Test
    void testIsEqualsWithSameValueMustReturnTrue() {
        EqualsIgnoreCaseComparator comparator = new EqualsIgnoreCaseComparator();
        Assertions.assertTrue(comparator.isEquals("value", "VALUE"));
    }
}
