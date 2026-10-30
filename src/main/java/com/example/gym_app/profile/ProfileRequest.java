package com.example.gym_app.profile;

import com.example.gym_app.exercise.Equipment;
import jakarta.validation.constraints.*;

import java.util.Set;

public record ProfileRequest(
        @NotNull Goal goal,
        @NotNull @Min(2) @Max(5) Integer daysPerWeek,
        @NotNull Integer sessionMinutes,
        @NotNull Set<@NotNull Equipment> equipment
) {
    @AssertTrue(message = "sessionMinutes must be one of 30, 45, 60, 75, 90")
    public boolean isSessionMinutesAllowed() {
        return sessionMinutes == null || ProfileRules.SESSION_MINUTES.contains(sessionMinutes);
    }
}