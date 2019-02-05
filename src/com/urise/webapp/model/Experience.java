package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Experience {
    private String organizationName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String post;
    private String homepage;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Experience(String organizationName, String startDate, String endDate, String description) {
        Objects.requireNonNull(organizationName, "organizationName must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(description, "description must not be null");

        this.organizationName = organizationName;
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
        this.description = description;
    }

    public Experience(String organizationName, String startDate, String endDate, String description,
                      String post, String homepage) {
        this(organizationName, startDate, endDate, description);
        this.post = post;
        this.homepage = homepage;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "Организация: " + organizationName +
                "\nПериод: " + startDate.format(DateTimeFormatter.ofPattern("MM/yyyy")) +
                " - " + endDate.format(DateTimeFormatter.ofPattern("MM/yyyy")) +
                "\nОписание: " + description +
                "\nCайт: " + homepage +
                "\nДолжность: " + post;
    }
}