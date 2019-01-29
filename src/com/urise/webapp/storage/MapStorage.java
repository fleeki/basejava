package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    public Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExistKey(Object key) {
        String value = (String) key;
        return storage.containsKey(value);
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        String value = (String) key;
        storage.put(value, resume);
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        String value = (String) key;
        storage.replace(value, resume);
    }

    @Override
    protected Resume getElement(Object key) {
        String value = (String) key;
        return storage.get(value);
    }

    @Override
    protected void deleteElement(Object key) {
        String value = (String) key;
        storage.remove(value);
    }
}