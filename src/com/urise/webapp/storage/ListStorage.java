package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    public List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void deleteElement(Object key) {
        int value = (Integer) key;
        storage.remove(value);
    }

    @Override
    protected boolean isExistKey(Object key) {
        return (Integer) key >= 0;
    }
}