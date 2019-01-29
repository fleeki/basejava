package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object key = getExistKey(resume.getUuid());
        insertElement(key, resume);
    }

    @Override
    public void update(Resume resume) {
        Object key = getNotExistKey(resume.getUuid());
        updateElement(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getNotExistKey(uuid);
        return getElement(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getNotExistKey(uuid);
        deleteElement(key);
    }

    private Object getExistKey(String uuid) {
        Object key = getKey(uuid);
        if (isExistKey(key)) {
            throw new ExistStorageException(uuid);
        } else {
            return key;
        }
    }

    private Object getNotExistKey(String uuid) {
        Object key = getKey(uuid);
        if (!isExistKey(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            return key;
        }
    }

    protected abstract boolean isExistKey(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract void insertElement(Object key, Resume resume);

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);
}