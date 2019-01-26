package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        int actual = storage.size();
        Assert.assertEquals(0, actual);
    }

    @Test (expected = StorageException.class)
    public void save() {
        int size = storage.size();
        try {
            for (int i = size; i < 10_000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }

        storage.save(new Resume());
    }

    @Test (expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid3"));
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2");
        storage.update(resume);
    }

    @Test (expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void get() {
        Resume actual = storage.get("uuid2");
        Resume expected = new Resume("uuid2");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete("uuid2");
        int actual = storage.size();
        Assert.assertEquals(2, actual);
    }

    @Test (expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{new Resume("uuid1"), new Resume("uuid2"),
                new Resume("uuid3")};
        Resume[] actual = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void size() {
        int actual = storage.size();
        Assert.assertEquals(3, actual);
    }
}