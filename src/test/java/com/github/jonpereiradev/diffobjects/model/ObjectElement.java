package com.github.jonpereiradev.diffobjects.model;


import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;

import java.util.Objects;

public class ObjectElement extends ParentObjectElement {

    private final String name;

    public ObjectElement(String name) {
        this.name = name;
    }

    @DiffMapping
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectElement that = (ObjectElement) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
