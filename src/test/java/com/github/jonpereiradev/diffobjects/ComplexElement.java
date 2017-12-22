package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;

import java.util.ArrayList;
import java.util.List;

public class ComplexElement extends ParentObjectElement {

    private final ObjectElement objectElement;
    private final List<ObjectElement> objectElementList;
    private final List<ObjectElement> objectElementListByName;

    public ComplexElement(ObjectElement objectElement) {
        this.objectElement = objectElement;
        this.objectElementList = new ArrayList<>();
        this.objectElementListByName = new ArrayList<>();
    }

    public ComplexElement(List<ObjectElement> objectElementList) {
        this.objectElement = null;
        this.objectElementList = objectElementList;
        this.objectElementListByName = new ArrayList<>();
    }

    @DiffMapping("name")
    public ObjectElement getObjectElement() {
        return objectElement;
    }

    @DiffMapping
    public List<ObjectElement> getObjectElementList() {
        return objectElementList;
    }

    @DiffMapping("name")
    public List<ObjectElement> getObjectElementListByName() {
        return objectElementListByName;
    }
}
