package com.urise.webapp.model;

import java.util.*;

public class Resume {
    private final String uuid;
    private String fullName;
    private final Map<ContactType, ContactSection> contacts = new HashMap<>();
    private final Map<SectionType, TextSection> textSection = new HashMap<>();
    private final Map<SectionType, ListSection> listSection = new HashMap<>();
    private final Map<SectionType, DateSection> dateSection = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, ContactSection> getContacts() {
        return contacts;
    }

    public Map<SectionType, TextSection> getTextSection() {
        return textSection;
    }

    public Map<SectionType, ListSection> getListSection() {
        return listSection;
    }

    public Map<SectionType, DateSection> getDateSection() {
        return dateSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "uuid = " + uuid + ", fullName = " + fullName;
    }
}