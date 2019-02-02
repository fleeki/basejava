package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertAndMoveElements(int searchKey, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteAndMoveElements(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}