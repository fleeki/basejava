package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private Resume[] storage = new Resume[10_000];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = searchUuid(r.getUuid());
        if (size == storage.length) {
            System.out.println("Base resume is full!");
        } else if (index != -1) {
            System.out.println("Resume " + r.getUuid() + " is already exists");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = searchUuid(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume " + r.getUuid() + " is not found!");
        }
    }

    public Resume get(String uuid) {
        int index = searchUuid(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " is not found!");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = searchUuid(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " is not found!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int searchUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}