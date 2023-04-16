package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.Test;

public class DiffQueryBuilderImplTest {

    @Test(expected = DiffException.class)
    public void testMustThrowExceptionWhenFieldNotFound() {
        DiffBuilderContext<ObjectElement> context = new DiffBuilderContext<>(ObjectElement.class);
        new DiffQueryBuilderImpl<>(context).find("not_exists");
    }

}