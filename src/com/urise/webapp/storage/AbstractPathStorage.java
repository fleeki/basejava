package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            LOG.warning(dir + " is not directory or is not writable");
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            LOG.warning("Path clear error");
            throw new StorageException("Path clear error", null, e);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list != null) {
            return list.length;
        } else {
            LOG.warning("Path read error");
            throw new StorageException("Path read error", null);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            List<Resume> listResume = new ArrayList<>();
            for (Path path : stream) {
                listResume.add(doGet(path));
            }
            return listResume;
        } catch (IOException e) {
            LOG.warning("Path read error");
            throw new StorageException("Path read error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExistSearchKey(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            LOG.warning("Couldn't create file " + path.getFileName().toString());
            throw new StorageException("Couldn't create file " + path, path.getFileName().toString(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(Files.newOutputStream(path), resume);
        } catch (IOException e) {
            LOG.warning("Path write error " + path.getFileName().toString());
            throw new StorageException("Path write error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            LOG.warning("Path read error " + path.getFileName().toString());
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOG.warning("Path delete error");
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}