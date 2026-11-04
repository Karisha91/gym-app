package com.example.gym_app.workout;

import com.example.gym_app.exercise.ExerciseResponse;
import com.example.gym_app.exercise.MovementPattern;

public record PlannedSlotResponse(
        MovementPattern pattern,
        boolean skipped,
        ExerciseResponse exercise,
        int sets,
        int minReps,
        int maxReps
) {
    public static PlannedSlotResponse from(PlannedSlot slot) {
        return new PlannedSlotResponse(
                slot.slotSpec().pattern(),
                slot.isSkipped(),
                slot.exercise().map(ExerciseResponse::from).orElse(null),
                slot.scheme().sets(),
                slot.scheme().minReps(),
                slot.scheme().maxReps()
        );
    }
}