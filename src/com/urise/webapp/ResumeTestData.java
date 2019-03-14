package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;

public class ResumeTestData {
    private static Resume resume;

    public static Resume fillResume(String uuid, String fullName) {
        resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "some phone number");
        resume.addContact(ContactType.SKYPE, "some skype");
        resume.addContact(ContactType.EMAIL, "some email");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("objective some content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal some content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("achievement item_1",
                "achievement item_2", "achievement item_3")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("qualifications item_1",
                "qualifications item_2", "qualifications item_3")));

        Organization.Position javaopsPosition = new Organization.Position("10/2013", "настоящее время", "some title",
                "javaops description");
        Organization javaops = new Organization("Java Online Projects", "http://javaops.ru/",
                javaopsPosition);

        Organization.Position wrikePosition = new Organization.Position("10/2014", "01/2016", "some title",
                "wrike description");
        Organization wrike = new Organization("Wrike", "https://www.wrike.com",
                wrikePosition);

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(javaops, wrike)));

        Organization.Position courseraPosition = new Organization.Position("03/2013", "05/2013", "some title",
                null);
        Organization coursera = new Organization("Coursera", "https://www.coursera.org",
                courseraPosition);

        Organization.Position itmoPosition_1 = new Organization.Position("09/1993", "07/1996", "some title",
                null);
        Organization.Position itmoPosition_2 = new Organization.Position("09/1987", "07/1993", "some title",
                null);
        Organization itmo = new Organization("Университет ИТМО", "http://www.ifmo.ru/",
                itmoPosition_1, itmoPosition_2);

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(coursera, itmo)));

        return resume;
    }

    public static Resume fillResumeUpdate(String uuid, String fullName) {
        resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "new phone number");
        resume.addContact(ContactType.SKYPE, "new skype");
        resume.addContact(ContactType.EMAIL, "new email");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("new objective some content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("new personal some content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("new achievement item_1",
                "new achievement item_2", "new achievement item_3")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("new qualifications item_1",
                "new qualifications item_2", "new qualifications item_3")));

        return resume;
    }
}