package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object key = getNotExistKey(resume.getUuid());
        insertElement(key, resume);
    }

    @Override
    public void update(Resume resume) {
        Object key = getExistKey(resume.getUuid());
        updateElement(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistKey(uuid);
        return getElement(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistKey(uuid);
        deleteElement(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedStorage = copyAllElements();

        sortedStorage.sort((o1, o2) -> {
            int temp = o1.getFullName().compareTo(o2.getFullName());
            if (temp == 0) {
                return o1.getUuid().compareTo(o2.getUuid());
            } else {
                return temp;
            }
        });

        return sortedStorage;
    }

    private Object getNotExistKey(String uuid) {
        Object key = getKey(uuid);
        if (isExistKey(key)) {
            throw new ExistStorageException(uuid);
        } else {
            return key;
        }
    }

    private Object getExistKey(String uuid) {
        Object key = getKey(uuid);
        if (!isExistKey(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            return key;
        }
    }

    protected abstract List<Resume> copyAllElements();

    protected abstract Object getKey(String uuid);

    protected abstract boolean isExistKey(Object key);

    protected abstract void insertElement(Object key, Resume resume);

    protected abstract void updateElement(Object key, Resume resume);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);
}