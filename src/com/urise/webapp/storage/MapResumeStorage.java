package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends MapStorage {

    @Override
    protected Object getKey(String uuid) {
        return storage.get(String.valueOf(uuid));
    }

    @Override
    protected boolean isExistKey(Object key) {
        return key != null;
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        super.insertElement(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        super.updateElement(((Resume) key).getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object key) {
        return super.getElement(((Resume) key).getUuid());
    }

    @Override
    protected void deleteElement(Object key) {
        super.deleteElement(((Resume) key).getUuid());
    }
}