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
    public List<Resume> doCopyAll() {
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
    protected void doSave(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected void doUpdate(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(uuid);
    }
}