package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    public Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(String.valueOf(uuid));
    }

    @Override
    protected boolean isExistSearchKey(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        storage.replace(searchKey.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }
}