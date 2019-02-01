package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, (o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));
    }

    @Override
    protected void insertAndMoveElements(int key, Resume resume) {
        int position = -key - 1;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
    }

    @Override
    protected void deleteAndMoveElements(int key) {
        int numMoved = size - key - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, key + 1, storage, key, numMoved);
        }
    }
}