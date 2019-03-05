package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? resumesPage() : storage.get(name).getFullName());
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
            sb.append("<tr><td>").append("<a href = \"resume?name=" + resume.getUuid() + "\">" + resume.getFullName()).append("</a></td><td align=center>").append(resume.getUuid()).append("</td></tr>");
        }
        return sb.toString();
    }
}