package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    public Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Base resume is full!", resume.getUuid());
        } else {
            insertAndMoveElements((Integer) key, resume);
            size++;
        }
    }

    @Override
    public void updateElement(Object key, Resume resume) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected Resume getElement(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected void deleteElement(Object key) {
        deleteAndMoveElements((Integer) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExistKey(Object key) {
        return (Integer) key >= 0;
    }

    protected abstract void insertAndMoveElements(int key, Resume resume);

    protected abstract void deleteAndMoveElements(int key);
}