package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private static final String DUMMY_UUID = "dummy";
    private static final Resume DUMMY_RESUME = new Resume(DUMMY_UUID);
    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        if (resume != storage.get("uuid4")) {
            Assert.fail("Expected resume not equals actual resume");
        }
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        int size = storage.size();
        try {
            for (int i = size; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Overflow happened before limit");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid3"));
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        if (resume != storage.get("uuid2")) {
            Assert.fail("Expected resume not equals actual resume");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(DUMMY_RESUME);
    }

    @Test
    public void get() {
        Resume actual = storage.get("uuid2");
        Resume expected = new Resume("uuid2");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY_UUID);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        Assert.assertEquals(2, storage.size());
        storage.get("uuid2");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY_UUID);
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{new Resume("uuid1"), new Resume("uuid2"),
                new Resume("uuid3")};
        Resume[] actual = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
        Assert.assertEquals(3, actual.length);
    }

    @Test
    public void size() {
        int actual = storage.size();
        Assert.assertEquals(3, actual);
    }
}