package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HtmlUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String viewContactToHtml(ContactType type, String value) {
        if (value == null) {
            return "";
        } else {
            switch (type) {
                case PHONE:
                    return "<img src=\"img/phone.png\" height=\"16\" align=\"left\" style=\"margin-right: 5px\">" + value;
                case SKYPE:
                    return "<img src=\"img/skype.png\" height=\"16\" align=\"left\" style=\"margin-right: 5px\"><a href='skype:" + value + "'>" + value + "</a>";
                case EMAIL:
                    return "<img src=\"img/email.png\" height=\"16\" align=\"left\" style=\"margin-right: 5px\"><a href='mailto:" + value + "'>" + value + "</a>";
                default:
                    return type.getTitle() + ": " + value;
            }
        }
    }

    public static String printTextSectionToHtml(SectionType type, Resume resume) {
        TextSection section = ((TextSection) resume.getSection(type));
        return section == null ? "" : section.getContent();
    }

    public static String printListSectionToHtml(SectionType type, Resume resume) {
        ListSection section = ((ListSection) resume.getSection(type));
        return section == null ? "" : String.join("\n", section.getItems());
    }

    public static String printStartDatePositionToHtml(LocalDate start) {
        return start.format(FORMATTER);
    }

    public static String printEndDatePositionToHtml(LocalDate end) {
        if (end.getYear() == 3000) {
            return "настоящее время";
        } else {
            return end.format(FORMATTER);
        }
    }
}