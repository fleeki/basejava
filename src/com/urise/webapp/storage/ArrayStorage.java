package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertAndMoveElements(Object key, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteAndMoveElements(Object key) {
        storage[(Integer) key] = storage[size - 1];
    }
}