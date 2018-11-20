package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import org.junit.Test;

import java.util.HashMap;

public class DiffQueryBuilderImplTest {

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenFieldNotFound() {
        new DiffQueryBuilderImpl("notexists", new HashMap<String, DiffMetadata>(), null);
    }

}