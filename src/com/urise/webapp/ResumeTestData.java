package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Map;

public class ResumeTestData {
    private static final Resume resume = new Resume("Григорий Кислин");

    public static void main(String[] args) {
        resume.getContacts().put(ContactType.PHONE, new ContactSection("+7(921) 855-0482"));
        resume.getContacts().put(ContactType.SKYPE, new ContactSection("grigory.kislin"));
        resume.getContacts().put(ContactType.EMAIL, new ContactSection("gkislin@yandex.ru"));

        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям."));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection("- С 2013 года: разработка " +
                "проектов \"Разработка Web приложения\", \"Java Enterprise\", \"Многомодульный maven\"."));

        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection("- JEE AS: " +
                "GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2."));

        Experience expWork1 = new Experience("Java Online Projects", "01/10/2013", "05/02/2019",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                "Автор проекта","http://javaops.ru/");
        resume.getSections().put(SectionType.EXPERIENCE, new ExperienceSection(expWork1));

        Experience education1 = new Experience("Coursera", "01/03/2013", "01/05/2013",
                "\"Functional Programming Principles in Scala\" by Martin Odersky.");
                resume.getSections().put(SectionType.EDUCATION, new ExperienceSection(education1));

        printContact(ContactType.PHONE);
        printContact(ContactType.SKYPE);
        printAllContacts();
        printTextSection(SectionType.OBJECTIVE);
        printTextSection(SectionType.PERSONAL);
        printListSection(SectionType.ACHIEVEMENT);
        printListSection(SectionType.QUALIFICATIONS);
        printExperienceSection(SectionType.EXPERIENCE);
        printExperienceSection(SectionType.EDUCATION);
    }

    private static void printContact(ContactType cntType) {
        System.out.println(resume.getContact(cntType));
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
        for (String str : listSection.getAllDescription()) {
            System.out.println(str);
        }
    }

    private static void printExperienceSection(SectionType type) {
        ExperienceSection expSection = (ExperienceSection) resume.getSection(type);
        System.out.println("\n" + type);
        for (Experience expWork : expSection.getAllExperience()) {
            System.out.println(expWork);
        }
    }
}