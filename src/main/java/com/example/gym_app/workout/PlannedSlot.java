package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import java.util.Optional;

public record PlannedSlot(SlotSpec slotSpec, Optional<Exercise> exercise) {
    public boolean isSkipped() {
        return exercise.isEmpty();
    }
}