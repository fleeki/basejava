package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {
    private static final Resume resume = new Resume("some name");

    public static void main(String[] args) {
        fillResume(resume);
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

    public static void fillResume(Resume resume) {
        resume.getContacts().put(ContactType.PHONE, "some phone number");
        resume.getContacts().put(ContactType.SKYPE, "some skype");
        resume.getContacts().put(ContactType.EMAIL, "some email");

        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("some content"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("some content"));

        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("item_1",
                "item_2", "item_3")));
        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("item_1",
                "item_2", "item_3")));

        Period javaopsPeriod = new Period("10/2013", "сейчас", "some title",
                "javaops description");
        Organization javaops = new Organization("Java Online Projects", "http://javaops.ru/",
                javaopsPeriod);

        Period wrikePeriod = new Period("10/2014", "1/2016", "some title",
                "wrike description");
        Organization wrike = new Organization("Wrike", "https://www.wrike.com",
                wrikePeriod);

        resume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(javaops, wrike)));

        Period courseraPeriod = new Period("3/2013", "5/2013", "some title",
                null);
        Organization coursera = new Organization("Coursera", "https://www.coursera.org",
                courseraPeriod);

        Period itmoPeriod_1 = new Period("9/1993", "7/1996", "some title",
                null);
        Period itmoPeriod_2 = new Period("9/1987", "7/1993", "some title",
                null);
        Organization itmo = new Organization("Университет ИТМО", "http://www.ifmo.ru/",
                itmoPeriod_1, itmoPeriod_2);

        resume.getSections().put(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(coursera, itmo)));
    }

    private static void printContact(ContactType cntType) {
        System.out.println(cntType + " " + resume.getContact(cntType));
    }

    private static void printAllContacts() {
        System.out.println("\nСписок всех контактов:");
        for (Map.Entry contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey() + " " + contact.getValue());
        }
    }

    private static void printTextSection(SectionType type) {
        System.out.println("\n" + type + " " + resume.getSections().get(type));
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