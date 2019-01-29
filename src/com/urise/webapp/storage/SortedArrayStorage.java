package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertAndMoveElements(Object key, Resume resume) {
        int position = -(Integer) key - 1;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
    }

    @Override
    protected void deleteAndMoveElements(Object key) {
        int numMoved = size - (Integer) key - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, (Integer) key + 1, storage, (Integer) key, numMoved);
        }
    }
}