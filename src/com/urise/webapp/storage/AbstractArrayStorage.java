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

    protected void insertElement(int index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Base resume is full!", resume.getUuid());
        } else {
            insertAndMoveElements(index, resume);
            size++;
        }
    }

    @Override
    public void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    @Override
    protected void deleteElement(int index) {
        deleteAndMoveElements(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertAndMoveElements(int index, Resume resume);

    protected abstract void deleteAndMoveElements(int index);
}