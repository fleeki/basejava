package com.urise.webapp.model;

public class Contacts {
    private String phone;
    private String skype;
    private String email;
    private String profileLinkedIn;
    private String profileGitHub;
    private String profileStackoverflow;
    private String homePage;

    public Contacts(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileLinkedIn() {
        return profileLinkedIn;
    }

    public void setProfileLinkedIn(String profileLinkedIn) {
        this.profileLinkedIn = profileLinkedIn;
    }

    public String getProfileGitHub() {
        return profileGitHub;
    }

    public void setProfileGitHub(String profileGitHub) {
        this.profileGitHub = profileGitHub;
    }

    public String getProfileStackoverflow() {
        return profileStackoverflow;
    }

    public void setProfileStackoverflow(String profileStackoverflow) {
        this.profileStackoverflow = profileStackoverflow;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
}