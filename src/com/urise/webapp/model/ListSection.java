package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection {
    private String description;
    private final List<String> list = new ArrayList<>();

    public ListSection(String description) {
        this.description = description;
        list.add(description);
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
}