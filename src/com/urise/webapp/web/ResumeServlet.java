package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? resumesPage() : uuidPage(name));
    }

    private String resumesPage() {
        List<Resume> resumes = storage.getAllSorted();
        return "<section>" +
                "<table width=\"400px\" border=\"1\" cellpadding=\"4\" style=\"border-collapse: collapse;\">" +
                "<caption>База данных резюме</caption>" +
                printAllResume(resumes) +
                "</table>" +
                "</section>";
    }

    private String printAllResume(List<Resume> resumes) {
        StringBuilder sb = new StringBuilder("<tr><th>Полное имя</th><th>Уникальный идентификатор</th></tr>");
        for (Resume resume : resumes) {
            sb.append("<tr><td>").append(resume.getFullName()).append("</td><td align=center>").append(resume.getUuid()).append("</td></tr>");
        }
        return sb.toString();
    }

    private String uuidPage(String name) {
        Resume resume = storage.get(name);
        return "<section><h1>" + resume.getFullName() + "</h1>" +
                printAllContacts(resume) +
                "<table>" +
                printAllSections(resume) +
                "</table>" +
                "</section>";
    }

    private String printAllContacts(Resume resume) {
        StringBuilder sb = new StringBuilder("<p>");
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            sb.append(contact.getKey()).append(": ").append(contact.getValue()).append("<br>");
        }
        return sb.append("</p>").toString();
    }

    private String printAllSections(Resume resume) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            printSection(sb, section.getKey(), section.getValue());
        }
        return sb.toString();
    }

    private void printSection(StringBuilder sb, SectionType type, AbstractSection sectionValue) {
        sb.append("<tr>").append("<td>").append("<h2 style=\"margin: 15px 0 5px;\">").append(type.getTitle()).append("</h2>").append("</td>").append("</tr>");
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                sb.append("<tr>").append("<td>").append(((TextSection) sectionValue).getContent()).append("</td>").append("</tr>");
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sb.append("<tr>").append("<td>").append("<ul style=\"margin: 0;\">");
                for (String item : ((ListSection) sectionValue).getItems()) {
                    sb.append("<li>").append(item).append("</li>");
                }
                sb.append("</ul>").append("</td>").append("</tr>");
                break;
        }
    }
}