package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.XmlStreamSerialization;

public class XmlFileStorageTest extends AbstractStorageTest {

    public XmlFileStorageTest() {
        super(new FileStorage(STORAGE_DIR), new XmlStreamSerialization());
    }
}