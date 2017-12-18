package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;

import java.util.ArrayList;
import java.util.List;

public class ComplexElement extends ParentObjectElement {

    private final ObjectElement objectElement;
    private final List<ObjectElement> objectElementList;

    public ComplexElement(ObjectElement objectElement) {
        this.objectElement = objectElement;
        this.objectElementList = new ArrayList<>();
    }

    public ComplexElement(List<ObjectElement> objectElementList) {
        this.objectElement = null;
        this.objectElementList = objectElementList;
    }

    @DiffMapping("name")
    public ObjectElement getObjectElement() {
        return objectElement;
    }

    @DiffMapping
    public List<ObjectElement> getObjectElementList() {
        return objectElementList;
    }
}
