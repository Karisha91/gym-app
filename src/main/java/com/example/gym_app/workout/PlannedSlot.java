package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import java.util.Optional;

public record PlannedSlot(SlotSpec slotSpec, Optional<Exercise> exercise, SetScheme scheme) {
    public boolean isSkipped() {
        return exercise.isEmpty();
    }
}