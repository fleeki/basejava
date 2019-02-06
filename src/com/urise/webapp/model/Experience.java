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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (organizationName != null ? !organizationName.equals(that.organizationName) : that.organizationName != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!post.equals(that.post)) return false;
        if (!homepage.equals(that.homepage)) return false;
        return formatter != null ? formatter.equals(that.formatter) : that.formatter == null;
    }

    @Override
    public int hashCode() {
        int result = organizationName != null ? organizationName.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + post.hashCode();
        result = 31 * result + homepage.hashCode();
        result = 31 * result + (formatter != null ? formatter.hashCode() : 0);
        return result;
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