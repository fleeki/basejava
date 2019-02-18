package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Скайп"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль LinkedIn"),
    STACKOVERFLOW("Профиль LinkedIn"),
    HOME_PAGE("Профиль LinkedIn");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}