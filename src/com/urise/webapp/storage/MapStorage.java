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
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExistKey(Object key) {
        return storage.containsKey(String.valueOf(key));
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        storage.put(String.valueOf(key), resume);
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        storage.replace(String.valueOf(key), resume);
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get(String.valueOf(key));
    }

    @Override
    protected void deleteElement(Object key) {
        storage.remove(String.valueOf(key));
    }
}