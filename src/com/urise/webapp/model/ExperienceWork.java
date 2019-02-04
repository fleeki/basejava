package com.urise.webapp.model;

public class ExperienceWork {
    private String organizationName;
    private String period;
    private String description;
    private String post;

    public ExperienceWork(String organizationName, String period, String description, String post) {
        this.organizationName = organizationName;
        this.period = period;
        this.description = description;
        this.post = post;
    }

    public ExperienceWork(String organizationName, String period, String description) {
        this.organizationName = organizationName;
        this.period = period;
        this.description = description;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String begin) {
        this.period = period;
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

    @Override
    public String toString() {
        return "Организация: " + organizationName +
                "\nПериод: " + period +
                "\nОписание: " + description +
                "\nДолжность: " + post;
    }
}