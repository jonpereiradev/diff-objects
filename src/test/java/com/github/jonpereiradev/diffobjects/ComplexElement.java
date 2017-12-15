package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.annotation.Diff;

import java.util.ArrayList;
import java.util.List;

public class ComplexElement {

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

    @Diff("name")
    public ObjectElement getObjectElement() {
        return objectElement;
    }

    @Diff
    public List<ObjectElement> getObjectElementList() {
        return objectElementList;
    }
}
