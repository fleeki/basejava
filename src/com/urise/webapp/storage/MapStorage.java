package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    public Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> copyAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExistSearchKey(Object searchKey) {
        return storage.containsKey(String.valueOf(searchKey));
    }

    @Override
    protected void insertElement(Object searchKey, Resume resume) {
        storage.put(String.valueOf(searchKey), resume);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.replace(String.valueOf(searchKey), resume);
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(String.valueOf(searchKey));
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(String.valueOf(searchKey));
    }
}