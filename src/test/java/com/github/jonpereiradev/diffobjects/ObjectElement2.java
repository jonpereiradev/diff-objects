package com.github.jonpereiradev.diffobjects;


import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectElement2 that = (ObjectElement2) o;

        return new EqualsBuilder()
            .append(getName(), that.getName())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getName())
            .toHashCode();
    }
}
