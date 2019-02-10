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
        Objects.requireNonNull(directory, " directory must not be null");
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
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        } else {
            LOG.warning("Directory read error");
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list != null) {
            return list.length;
        } else {
            LOG.warning("Directory read error");
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if (files != null) {
            List<Resume> listResume = new ArrayList<>(files.length);
            for (File file : files) {
                listResume.add(doGet(file));
            }
            return listResume;
        } else {
            LOG.warning("Directory read error");
            throw new StorageException("Directory read error", null);
        }
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
            LOG.warning("Couldn't create file " + file.getName());
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            LOG.warning("File write error " + file.getName());
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            LOG.warning("File read error " + file.getName());
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            LOG.warning("File delete error " + file.getName());
            throw new StorageException("File delete error", file.getName());
        }
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}