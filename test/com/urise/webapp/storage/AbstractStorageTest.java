package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    private static final String DUMMY_UUID = "dummy";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_1 = "Pol";
    private static final String FULLNAME_2 = "Andrew";
    private static final String FULLNAME_3 = "Pavel";
    private static final String FULLNAME_4 = "Max";
    private static final Resume DUMMY_RESUME = new Resume(DUMMY_UUID);
    private static final Resume RESUME_1 = new Resume(UUID_1, FULLNAME_1);
    private static final Resume RESUME_2 = new Resume(UUID_2, FULLNAME_2);
    private static final Resume RESUME_3 = new Resume(UUID_3, FULLNAME_3);
    private static final Resume RESUME_4 = new Resume(UUID_4, FULLNAME_4);
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
        Assert.assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test
    public void update() {
        Resume expected = new Resume(UUID_2);
        storage.update(expected);
        Assert.assertSame(expected, storage.get(UUID_2));
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
    public void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] actual = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
        Assert.assertEquals(3, actual.length);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = sort();
        List<Resume> actual = storage.getAllSorted();
        Assert.assertEquals(3, actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertSame(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    private List<Resume> sort() {
        List<Resume> list = new ArrayList<>();
        list.add(RESUME_1);
        list.add(RESUME_2);
        list.add(RESUME_3);

        list.sort((o1, o2) -> {
            int temp = o1.getFullName().compareTo(o2.getFullName());
            if (temp == 0) {
                return o1.getUuid().compareTo(o2.getUuid());
            } else {
                return temp;
            }
        });

        return list;
    }
}