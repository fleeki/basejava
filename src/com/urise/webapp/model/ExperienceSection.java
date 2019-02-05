package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExperienceSection extends Section {
    private final List<Experience> experience = new ArrayList<>();

    public ExperienceSection(Experience exp) {
        Objects.requireNonNull(exp, "exp must not be null");
        experience.add(exp);
    }

    public void addNewExperience(Experience newExp) {
        experience.add(newExp);
    }

    public void updateExperience(int index, Experience newExp) {
        experience.set(index, newExp);
    }

    public void deleteExperience(int index) {
        experience.remove(index);
    }

    public List<Experience> getAllExperience() {
        return experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return experience.equals(that.experience);
    }

    @Override
    public int hashCode() {
        return experience.hashCode();
    }
}