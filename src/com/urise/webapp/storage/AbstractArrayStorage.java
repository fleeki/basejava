package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected boolean isExistSearchKey(Integer index) {
        return index >= 0;
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            LOG.warning("Base resume is full!");
            throw new StorageException("Base resume is full!", resume.getUuid());
        } else {
            fillInsertedElement(index, resume);
            size++;
        }
    }

    @Override
    public void updateElement(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage[index];
    }

    @Override
    protected void deleteElement(Integer index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillInsertedElement(int index, Resume resume);

    protected abstract void fillDeletedElement(int index);
}