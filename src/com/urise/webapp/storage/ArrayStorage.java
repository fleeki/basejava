package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Base resume is full!");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (r.getUuid().equals(storage[i].getUuid())) {
                System.out.println("Resume " + r.getUuid() + " is already exists");
                return;
            }
        }
        storage[size] = r;
        size++;
    }

    public void update(String uuid, Resume r) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = r;
                return;
            }
        }
        System.out.println("Resume " + uuid + " is not found!");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        System.out.println("Resume " + uuid + " is not found!");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(storage, i + 1, storage, i, numMoved);
                }
                storage[--size] = null;
                return;
            }
        }
        System.out.println("Resume " + uuid + " is not found!");
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        if (size == 0) {
            System.out.println("Base resume is empty!");
        }
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}