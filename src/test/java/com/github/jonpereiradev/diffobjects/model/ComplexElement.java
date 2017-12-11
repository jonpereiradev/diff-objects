package com.github.jonpereiradev.diffobjects.model;

import com.github.jonpereiradev.diffobjects.annotation.Diff;

public class ComplexElement {

    private final ObjectElement objectElement;

    public ComplexElement(ObjectElement objectElement) {
        this.objectElement = objectElement;
    }

    @Diff("name")
    public ObjectElement getObjectElement() {
        return objectElement;
    }
}
