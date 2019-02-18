package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.DataStreamSerialization;

public class DataFileStorageTest extends AbstractStorageTest {

    public DataFileStorageTest() {
        super(new FileStorage(STORAGE_DIR), new DataStreamSerialization());
    }
}