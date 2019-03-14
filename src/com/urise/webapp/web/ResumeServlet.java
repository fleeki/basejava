package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = HtmlUtil.isEmpty(uuid);
        Resume resume;

        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.addContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(value.trim().split("\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");

                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String index = type.name() + i;
                                String[] startDates = request.getParameterValues(index + "startDate");
                                String[] endDates = request.getParameterValues(index + "endDate");
                                String[] titles = request.getParameterValues(index + "title");
                                String[] descriptions = request.getParameterValues(index + "description");

                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.dateFormat(startDates[j]),
                                                DateUtil.dateFormat(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }

                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }

                        resume.addSection(type, new OrganizationSection(organizations));
                        break;
                }
            }
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume resume;

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        } else {
            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    return;
                case "add":
                    resume = Resume.EMPTY;
                    break;
                case "view":
                    resume = storage.get(uuid);
                    break;
                case "edit":
                    resume = storage.get(uuid);
                    for (SectionType type : SectionType.values()) {
                        AbstractSection section = resume.getSection(type);
                        switch (type) {
                            case OBJECTIVE:
                            case PERSONAL:
                                if (section == null) {
                                    section = TextSection.EMPTY;
                                }
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                if (section == null) {
                                    section = ListSection.EMPTY;
                                }
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                OrganizationSection organizationSection = (OrganizationSection) section;
                                List<Organization> emptyOrganizations = new ArrayList<>();
                                emptyOrganizations.add(Organization.EMPTY);

                                if (organizationSection != null) {
                                    for (Organization organization : organizationSection.getOrganizations()) {
                                        List<Organization.Position> emptyPositions = new ArrayList<>();
                                        emptyPositions.add(Organization.Position.EMPTY);
                                        emptyPositions.addAll(organization.getPositions());
                                        emptyOrganizations.add(new Organization(organization.getHomePage(), emptyPositions));
                                    }
                                }

                                section = new OrganizationSection(emptyOrganizations);
                                break;
                        }
                        resume.addSection(type, section);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}