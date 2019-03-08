package com.urise.webapp.util;

import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;

public class HtmlUtil {

    public static String viewSection(SectionType type, Resume resume) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return ((TextSection) resume.getSection(type)).getContent();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return "<ul>" + ((ListSection) resume.getSection(type)).getItems().stream()
                        .reduce("", (acc, str) -> acc + "<li>" + str + "</li>") + "</ul>";
            case EXPERIENCE:
            case EDUCATION:
                return "";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String printTextSection(SectionType type, Resume resume) {
        TextSection section = ((TextSection) resume.getSection(type));
        return section == null ? "" : section.getContent();
    }

    public static String printListSection(SectionType type, Resume resume) {
        ListSection section = ((ListSection) resume.getSection(type));
        return section == null ? "" : String.join("\n", section.getItems());
    }
}