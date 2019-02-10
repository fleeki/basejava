package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {
    private static Resume resume;

    public static void main(String[] args) {
        fillResume("uuid", "some name");
        printContact(ContactType.PHONE);
        printContact(ContactType.SKYPE);
        printAllContacts();
        printTextSection(SectionType.OBJECTIVE);
        printTextSection(SectionType.PERSONAL);
        printListSection(SectionType.ACHIEVEMENT);
        printListSection(SectionType.QUALIFICATIONS);
        printOrganizationSection(SectionType.EXPERIENCE);
        printOrganizationSection(SectionType.EDUCATION);
    }

    public static Resume fillResume(String uuid, String fullName) {
        resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "some phone number");
        resume.addContact(ContactType.SKYPE, "some skype");
        resume.addContact(ContactType.EMAIL, "some email");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("some content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("some content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("item_1",
                "item_2", "item_3")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("item_1",
                "item_2", "item_3")));

        Organization.Position javaopsPosition = new Organization.Position("10/2013", "сейчас", "some title",
                "javaops description");
        Organization javaops = new Organization("Java Online Projects", "http://javaops.ru/",
                javaopsPosition);

        Organization.Position wrikePosition = new Organization.Position("10/2014", "1/2016", "some title",
                "wrike description");
        Organization wrike = new Organization("Wrike", "https://www.wrike.com",
                wrikePosition);

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(javaops, wrike)));

        Organization.Position courseraPosition = new Organization.Position("3/2013", "5/2013", "some title",
                null);
        Organization coursera = new Organization("Coursera", "https://www.coursera.org",
                courseraPosition);

        Organization.Position itmoPosition_1 = new Organization.Position("9/1993", "7/1996", "some title",
                null);
        Organization.Position itmoPosition_2 = new Organization.Position("9/1987", "7/1993", "some title",
                null);
        Organization itmo = new Organization("Университет ИТМО", "http://www.ifmo.ru/",
                itmoPosition_1, itmoPosition_2);

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(coursera, itmo)));

        return resume;
    }

    private static void printContact(ContactType cntType) {
        System.out.println(cntType + " " + resume.getContact(cntType));
    }

    private static void printAllContacts() {
        System.out.println("\nСписок всех контактов:");
        for (Map.Entry contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact.getKey() + " " + contact.getValue());
        }
    }

    private static void printTextSection(SectionType type) {
        TextSection textSection = (TextSection) resume.getSection(type);
        System.out.println("\n" + type + " " + textSection.getContent());
    }

    private static void printListSection(SectionType type) {
        ListSection listSection = (ListSection) resume.getSection(type);
        System.out.println("\n" + type);
        for (String item : listSection.getItems()) {
            System.out.println(item);
        }
    }

    private static void printOrganizationSection(SectionType type) {
        OrganizationSection organizationSection = (OrganizationSection) resume.getSection(type);
        System.out.println("\n" + type);
        for (Organization organization : organizationSection.getOrganizations()) {
            System.out.println(organization);
        }
    }
}