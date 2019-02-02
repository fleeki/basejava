package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExistSearchKey(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected void insertElement(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected void updateElement(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(uuid);
    }
}