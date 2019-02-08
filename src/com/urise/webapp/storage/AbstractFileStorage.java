package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            LOG.warning(directory + " is not directory");
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            LOG.warning(directory + " is not readable/writable");
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        } else {
            this.directory = directory;
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return directory.list().length;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            try {
                listResume.add(doRead(file));
            } catch (IOException e) {
                LOG.warning("IO error " + file.getName());
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return listResume;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExistSearchKey(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            LOG.warning("IO error " + file.getName());
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            LOG.warning("IO error " + file.getName());
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            LOG.warning("IO error " + file.getName());
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}