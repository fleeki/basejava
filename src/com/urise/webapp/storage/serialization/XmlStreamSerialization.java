package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerialization implements SerializationStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(Resume.class, TextSection.class, ListSection.class,
                OrganizationSection.class, Organization.class,
                Organization.Position.class, Link.class);
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}