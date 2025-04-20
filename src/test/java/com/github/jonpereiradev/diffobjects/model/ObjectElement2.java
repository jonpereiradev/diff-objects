package com.github.jonpereiradev.diffobjects.model;


import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;

import java.util.Objects;


public class ObjectElement2 {

    private final String name;
    private final String name2;

    public ObjectElement2(String name, String name2) {
        this.name = name;
        this.name2 = name2;
    }

    @DiffMapping
    public String getName() {
        return name;
    }

    @DiffMapping
    public String getName2() {
        return name2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectElement2 that = (ObjectElement2) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
