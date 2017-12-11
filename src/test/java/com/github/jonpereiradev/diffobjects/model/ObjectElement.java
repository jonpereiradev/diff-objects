package com.github.jonpereiradev.diffobjects.model;

import com.github.jonpereiradev.diffobjects.annotation.Diff;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ObjectElement {

    private final String name;

    public ObjectElement(String name) {
        this.name = name;
    }

    @Diff
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ObjectElement that = (ObjectElement) o;

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
