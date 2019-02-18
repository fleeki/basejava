package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.JsonStreamSerialization;

public class JsonFileStorageTest extends AbstractStorageTest {

    public JsonFileStorageTest() {
        super(new FileStorage(STORAGE_DIR), new JsonStreamSerialization());
    }
}