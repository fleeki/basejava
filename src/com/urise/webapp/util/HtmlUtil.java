package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.urise.webapp.util.DateUtil.NOW;

public class HtmlUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String viewContactToHtml(ContactType type, String value) {
        if (value == null) {
            return "";
        } else {
            String title = type.getTitle().toLowerCase();
            switch (type) {
                case PHONE:
                    return printLink("phone", value);
                case EMAIL:
                    return printLink("email", "'mailto:" + value + "'", title, value);
                case SKYPE:
                    return printLink("skype", "'skype:" + value + "'", title, value);
                case HOME_PHONE:
                    return printLink("homephone", value);
                case MOBILE:
                    return printLink("mobile", value);
                case LINKEDIN:
                    return printLink("lin", value, title, value);
                case GITHUB:
                    return printLink("gh", value, title, value);
                case STACKOVERFLOW:
                    return printLink("so", value, title, value);
                case HOME_PAGE:
                    return printLink("homepage", value, title, value);
                default:
                    return type.getTitle() + ": " + value;
            }
        }
    }

    private static String printLink(String img, String value) {
        return "<img src=\"img/" + img + ".png\">" + value;
    }

    private static String printLink(String img, String href, String title, String value) {
        return "<img src=\"img/" + img + ".png\"><a href=\"" + href + "\" title=\"" + title + "\">" + value + "</a>";
    }

    public static String printDatePositionToHtml(LocalDate date) {
        if (date == null) {
            return "";
        } else {
            return date.equals(NOW) ? "настоящее время" : date.format(FORMATTER);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}