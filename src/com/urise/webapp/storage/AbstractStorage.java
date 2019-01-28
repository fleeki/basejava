package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getExistIndex(resume.getUuid());
        insertElement(index, resume);
    }

    @Override
    public void update(Resume resume) {
        int index = getNotExistIndex(resume.getUuid());
        updateElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getNotExistIndex(uuid);
        return getElement(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getNotExistIndex(uuid);
        deleteElement(index);
    }

    private int getExistIndex(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else {
            return index;
        }
    }

    private int getNotExistIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void updateElement(int index, Resume resume);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);
}