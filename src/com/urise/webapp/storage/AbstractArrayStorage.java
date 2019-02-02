package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public int size() {
        return size;
    }

    @Override
    protected List<Resume> copyAllElements() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExistSearchKey(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void insertElement(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Base resume is full!", resume.getUuid());
        } else {
            fillInsertedElement((Integer) searchKey, resume);
            size++;
        }
    }

    @Override
    public void updateElement(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void deleteElement(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillInsertedElement(int searchKey, Resume resume);

    protected abstract void fillDeletedElement(int searchKey);
}