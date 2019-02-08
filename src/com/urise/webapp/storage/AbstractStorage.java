package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    protected static final Comparator<Resume> COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> sortedStorage = doCopyAll();
        sortedStorage.sort(COMPARATOR);
        return sortedStorage;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExistSearchKey(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist!");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExistSearchKey(searchKey)) {
            LOG.warning("Resume " + uuid + " not found!");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExistSearchKey(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);
}