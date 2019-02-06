package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "description must not be null");
        this.list = list;
    }

    public void addNewDescription(String newDescription) {
        list.add(newDescription);
    }

    public void updateDescription(int index, String newDescription) {
        list.set(index, newDescription);
    }

    public void deleteDescription(int index) {
        list.remove(index);
    }

    public List<String> getAllDescription() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}