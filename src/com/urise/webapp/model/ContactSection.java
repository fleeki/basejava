package com.urise.webapp.model;

import java.util.Objects;

public class ContactSection {
    private String contactValue;

    public ContactSection(String contactValue) {
        Objects.requireNonNull(contactValue, "contactValue must not be null");
        this.contactValue = contactValue;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactSection that = (ContactSection) o;

        return contactValue.equals(that.contactValue);
    }

    @Override
    public int hashCode() {
        return contactValue.hashCode();
    }

    @Override
    public String toString() {
        return contactValue;
    }
}