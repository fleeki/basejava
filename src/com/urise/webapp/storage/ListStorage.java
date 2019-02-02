package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    public List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> copyAllElements() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExistSearchKey(Integer index) {
        return index >= 0;
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(Integer index) {
        storage.remove(index.intValue());
    }
}