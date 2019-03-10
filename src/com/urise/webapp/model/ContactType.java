package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Почта"),
    SKYPE("Скайп"),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    HOME_PAGE("Домашняя страница"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}