package com.urise.webapp.model;

public class ContactSection {
    private String contactValue;

    public ContactSection(String contactValue) {
        this.contactValue = contactValue;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    @Override
    public String toString() {
        return contactValue;
    }
}