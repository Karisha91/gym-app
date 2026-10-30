package com.example.gym_app.profile;

import com.example.gym_app.exercise.Equipment;

import java.util.Set;
import java.util.TreeSet;

public record ProfileResponse(
        Goal goal,
        ExperienceLevel experienceLevel,
        int daysPerWeek,
        int sessionMinutes,
        Set<Equipment> equipment
) {
    public static ProfileResponse from(Profile p) {
        return new ProfileResponse(p.getGoal(), p.getExperienceLevel(),
                p.getDaysPerWeek(), p.getSessionMinutes(),
                new TreeSet<>(p.getEquipment()));   // copies while the session is still open
    }
}