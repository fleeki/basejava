package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class DateSection {
    private final List<ExperienceWork> experience = new ArrayList<>();

    public DateSection(ExperienceWork expWork) {
        experience.add(expWork);
    }

    public void addNewExperience(ExperienceWork newExpWork) {
        experience.add(newExpWork);
    }

    public void updateExperience(int index, ExperienceWork newExpWork) {
        experience.set(index, newExpWork);
    }

    public void deleteExperience(int index) {
        experience.remove(index);
    }

    public List<ExperienceWork> getAllExperience() {
        return experience;
    }
}