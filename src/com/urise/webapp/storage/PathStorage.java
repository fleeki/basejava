package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialization.SerializationStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializationStrategy strategy;

    protected PathStorage(String dir) {
        Objects.requireNonNull(dir, " directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            LOG.warning(dir + " is not directory or is not writable");
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
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
            LOG.warning("Couldn't create file " + getFileName(path));
            throw new StorageException("Couldn't create file " + path, getFileName(path), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            strategy.doWrite(Files.newOutputStream(path), resume);
        } catch (IOException e) {
            LOG.warning("Path write error " + getFileName(path));
            throw new StorageException("Path write error", getFileName(path), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            LOG.warning("Path read error " + getFileName(path));
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOG.warning("Path delete error " + getFileName(path));
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    protected void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            LOG.warning("Path read error");
            throw new StorageException("Path read error", e);
        }
    }
}