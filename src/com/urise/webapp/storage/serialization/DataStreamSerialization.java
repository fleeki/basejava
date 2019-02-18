package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                writeSection(dos, section.getKey(), section.getValue());
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                String sectionType = dis.readUTF();
                resume.addSection(SectionType.valueOf(sectionType), readSection(dis, sectionType));
            }
            return resume;
        }
    }

    private void writeSection(DataOutputStream dos, SectionType type, AbstractSection sectionValue) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF(type.name());
                dos.writeUTF(((TextSection) sectionValue).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                dos.writeUTF(type.name());
                ListSection listSection = (ListSection) sectionValue;
                dos.writeInt(listSection.getItems().size());
                for (String item : listSection.getItems()) {
                    dos.writeUTF(item);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                dos.writeUTF(type.name());
                OrganizationSection organizationSection = (OrganizationSection) sectionValue;
                dos.writeInt(organizationSection.getOrganizations().size());
                for (Organization organization : organizationSection.getOrganizations()) {
                    dos.writeUTF(organization.getHomePage().getName());
                    dos.writeUTF(organization.getHomePage().getUrl() != null ? organization.getHomePage().getUrl() : "null");

                    dos.writeInt(organization.getPositions().size());
                    for (Organization.Position position : organization.getPositions()) {
                        writeLocalDate(dos, position.getStartDate());
                        writeLocalDate(dos, position.getEndDate());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription() != null ? position.getDescription() : "null");
                    }
                }
                break;
        }
    }

    private AbstractSection readSection(DataInputStream dis, String type) throws IOException {
        switch (SectionType.valueOf(type)) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                int itemsSize = dis.readInt();
                List<String> items = new ArrayList<>(itemsSize);
                for (int i = 0; i < itemsSize; i++) {
                    items.add(dis.readUTF());
                }
                return new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                int organizationSize = dis.readInt();
                List<Organization> organizations = new ArrayList<>(organizationSize);
                for (int i = 0; i < organizationSize; i++) {
                    String name = dis.readUTF();
                    String url = dis.readUTF();
                    Link link = new Link(name, "null".equals(url) ? null : url);

                    int positionSize = dis.readInt();
                    List<Organization.Position> positions = new ArrayList<>(positionSize);
                    for (int j = 0; j < positionSize; j++) {
                        LocalDate startDate = of(dis.readInt(), Month.valueOf(dis.readUTF()));
                        LocalDate endDate = of(dis.readInt(), Month.valueOf(dis.readUTF()));
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        Organization.Position position = new Organization.Position(startDate, endDate, title,
                                "null".equals(description) ? null : description);
                        positions.add(position);
                    }
                    organizations.add(new Organization(link, positions));
                }
                return new OrganizationSection(organizations);
            default:
                throw new IllegalStateException();
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeUTF(date.getMonth().name());
    }
}