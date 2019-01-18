import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int index = -1;
    int size = 0;

    void clear() {
        Arrays.fill(storage, null);
        size = 0;
        index = -1;
    }

    void save(Resume r) {
        if (index + 1 == storage.length) {
            System.out.println("Base resume is full!");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (r.uuid.equals(storage[i].uuid)) {
                System.out.println("Resume " + r.uuid + " is already exists");
                return;
            }
        }
        storage[++index] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        System.out.println("Resume " + uuid + " is not found!");
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                int numMoved = size - i - 1;
                if (numMoved > 0)
                    System.arraycopy(storage, i+1, storage, i, numMoved);
                storage[--size] = null;
                index--;
                return;
            }
        }
        System.out.println("Resume " + uuid + " is not found!");
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size == 0)
            System.out.println("Base resume is empty!");
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
