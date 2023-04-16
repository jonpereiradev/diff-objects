package com.github.jonpereiradev.diffobjects.model;


import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;

import java.util.ArrayList;
import java.util.List;


public class ComplexElement extends ParentObjectElement {

    private final ObjectElement objectElement;
    private final List<ObjectElement> objectElementList;
    private final List<ObjectElement> objectElementListByName;
    private final String notAccessible;
    private final String withArgs;

    public ComplexElement(ObjectElement objectElement) {
        this.objectElement = objectElement;
        this.objectElementList = new ArrayList<>();
        this.objectElementListByName = new ArrayList<>();
        this.notAccessible = null;
        this.withArgs = null;
    }

    public ComplexElement(List<ObjectElement> objectElementList) {
        this.objectElement = null;
        this.objectElementList = objectElementList;
        this.objectElementListByName = new ArrayList<>();
        this.notAccessible = null;
        this.withArgs = null;
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

    String getNotAccessible() {
        return notAccessible;
    }

    public String getWithArgs(String args) {
        return withArgs + args;
    }
}
