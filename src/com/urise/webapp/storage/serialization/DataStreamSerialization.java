package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.util.DateUtil.of;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, resume.getSections().entrySet(), entry -> writeSection(dos, entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readType(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readType(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });

            return resume;
        }
    }

    private void writeSection(DataOutputStream dos, SectionType type, AbstractSection sectionValue) throws IOException {
        dos.writeUTF(type.name());
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF(((TextSection) sectionValue).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeCollection(dos, ((ListSection) sectionValue).getItems(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCollection(dos, ((OrganizationSection) sectionValue).getOrganizations(), organization -> {
                    dos.writeUTF(organization.getHomePage().getName());
                    dos.writeUTF(organization.getHomePage().getUrl());

                    writeCollection(dos, organization.getPositions(), position -> {
                        writeLocalDate(dos, position.getStartDate());
                        writeLocalDate(dos, position.getEndDate());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription());
                    });
                });
                break;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readCollection(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readCollection(dis, () -> {
                    Link link = new Link(dis.readUTF(), dis.readUTF());
                    return new Organization(link, readCollection(dis, () -> {
                        LocalDate startDate = of(dis.readInt(), Month.valueOf(dis.readUTF()));
                        LocalDate endDate = of(dis.readInt(), Month.valueOf(dis.readUTF()));
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        return new Organization.Position(startDate, endDate, title, description);
                    }));
                }));
            default:
                throw new IllegalStateException();
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeUTF(date.getMonth().name());
    }

    private interface Writable<T> {
        void write(T t) throws IOException;
    }

    private interface Readable<T> {
        T read() throws IOException;
    }

    private interface ElementProcessor {
        void read() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writable<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private <T> List<T> readCollection(DataInputStream dis, Readable<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readType(DataInputStream dis, ElementProcessor element) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            element.read();
        }
    }
}