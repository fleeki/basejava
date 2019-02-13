package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.fillResume;
import static com.urise.webapp.storage.AbstractStorage.COMPARATOR;

public abstract class AbstractStorageTest {
    private static final String DUMMY_UUID = "dummy";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume DUMMY_RESUME = fillResume(DUMMY_UUID, "dummy Name");
    private static final Resume RESUME_1 = fillResume(UUID_1, "Pol");
    private static final Resume RESUME_2 = fillResume(UUID_2, "Andrew");
    private static final Resume RESUME_3 = fillResume(UUID_3, "Pavel");
    private static final Resume RESUME_4 = fillResume(UUID_4, "Max");
    protected static final File STORAGE_DIR = new File ("D:\\Java\\My_program\\basejava\\storage");
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
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
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test
    public void update() {
        Resume expected = fillResume(UUID_2, "new Name");
        storage.update(expected);
        Assert.assertEquals(expected, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(DUMMY_RESUME);
    }

    @Test
    public void get() {
        Resume actual = storage.get(UUID_2);
        Assert.assertEquals(RESUME_2, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY_UUID);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY_UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        expected.sort(COMPARATOR);
        List<Resume> actual = storage.getAllSorted();
        Assert.assertEquals(3, actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}