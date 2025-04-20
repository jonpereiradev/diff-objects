package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.DiffException;
import com.github.jonpereiradev.diffobjects.model.ObjectElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiffQueryBuilderImplTest {

    @Test
    void testMustThrowExceptionWhenFieldNotFound() {
        DiffBuilderContext<ObjectElement> context = new DiffBuilderContext<>(ObjectElement.class);
        Assertions.assertThrows(DiffException.class, () -> new DiffQueryBuilderImpl<>(context).find("not_exists"));
    }

}