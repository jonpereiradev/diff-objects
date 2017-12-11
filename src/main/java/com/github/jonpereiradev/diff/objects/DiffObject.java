package com.github.jonpereiradev.diff.objects;

import java.util.Collection;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffObject {

    private String group;
    private String name;
    private Object before;
    private Object after;

    public DiffObject(DiffProperty annotation, Object before, Object after) {
        this.group = annotation.group();
        this.name = annotation.name();
        this.before = before;
        this.after = after;
    }

    public boolean isCollection() {
        return before instanceof Collection || after instanceof Collection;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }

}
