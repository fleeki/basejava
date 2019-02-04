package com.urise.webapp;

import com.urise.webapp.model.*;

import java.sql.SQLOutput;
import java.util.Map;

public class ResumeTestData {
    private static final Resume resume = new Resume("Григорий Кислин");

    public static void main(String[] args) {
        resume.getContacts().put(ContactType.PHONE, new ContactSection("+7(921) 855-0482"));
        resume.getContacts().put(ContactType.SKYPE, new ContactSection("grigory.kislin"));
        resume.getContacts().put(ContactType.EMAIL, new ContactSection("gkislin@yandex.ru"));

        resume.getTextSection().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям."));
        resume.getTextSection().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.getListSection().put(SectionType.ACHIEVEMENT, new ListSection("- С 2013 года: разработка " +
                "проектов \"Разработка Web приложения\", \"Java Enterprise\", \"Многомодульный maven\"."));
        resume.getListSection().get(SectionType.ACHIEVEMENT).addNewDescription("- Реализация двухфакторной " +
                "аутентификации для онлайн платформы управления проектами Wrike.");
        resume.getListSection().get(SectionType.ACHIEVEMENT).addNewDescription("- Налаживание процесса " +
                "разработки и непрерывной интеграции ERP системы River BPM.");

        resume.getListSection().put(SectionType.QUALIFICATIONS, new ListSection("- JEE AS: " +
                "GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2."));
        resume.getListSection().get(SectionType.QUALIFICATIONS).addNewDescription("- Version control: Subversion, " +
                "Git, Mercury, ClearCase, Perforce.");
        resume.getListSection().get(SectionType.QUALIFICATIONS).addNewDescription("- DB: PostgreSQL (наследование, " +
                "pgplsql, PL/Python), Redis (Jedis), H2, Oracle.");


        ExperienceWork expWork1 = new ExperienceWork("Java Online Projects", "10/2013 - Сейчас",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                "Автор проекта");
        ExperienceWork expWork2 = new ExperienceWork("Wrike", "10/2014 - 01/2016",
                "Проектирование и разработка онлайн платформы управления проектами Wrike",
                "Старший разработчик (backend)");
        resume.getDateSection().put(SectionType.EXPERIENCE, new DateSection(expWork1));
        resume.getDateSection().get(SectionType.EXPERIENCE).addNewExperience(expWork2);

        ExperienceWork education1 = new ExperienceWork("Coursera", "03/2013 - 05/2013",
                "\"Functional Programming Principles in Scala\" by Martin Odersky.");
        ExperienceWork education2 = new ExperienceWork("Luxoft", "03/2011 - 04/2011",
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        resume.getDateSection().put(SectionType.EDUCATION, new DateSection(education1));
        resume.getDateSection().get(SectionType.EDUCATION).addNewExperience(education2);

        printContact(ContactType.PHONE);
        printAllContacts();
        printTextSection(SectionType.OBJECTIVE);
        printTextSection(SectionType.PERSONAL);
        printListSection(SectionType.ACHIEVEMENT);
        printListSection(SectionType.QUALIFICATIONS);
        printDateSection(SectionType.EXPERIENCE);
        printDateSection(SectionType.EDUCATION);

    }

    private static void printContact(ContactType cntType) {
        System.out.println(resume.getContacts().get(cntType));
    }

    private static void printAllContacts() {
        for (Map.Entry contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey() + " " + contact.getValue());
        }
    }

    private static void printTextSection(SectionType type) {
        System.out.println("\n" + type + " " + resume.getTextSection().get(type));
    }

    private static void printListSection(SectionType type) {
        System.out.println("\n" + type);
        for (String str : resume.getListSection().get(type).getAllDescription()) {
            System.out.println(str);
        }
    }

    private static void printDateSection(SectionType type) {
        System.out.println("\n" + type);
        for (ExperienceWork expWork : resume.getDateSection().get(type).getAllExperience()) {
            System.out.println(expWork);
        }
    }
}