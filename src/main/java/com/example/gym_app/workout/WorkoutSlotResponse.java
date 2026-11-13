package com.example.gym_app.workout;

import com.example.gym_app.exercise.ExerciseResponse;
import com.example.gym_app.exercise.MovementPattern;

public record WorkoutSlotResponse(
        Long id,
        MovementPattern pattern,
        boolean skipped,
        ExerciseResponse exercise,
        int sets,
        int minReps,
        int maxReps,
        Double targetWeightKg
) {
    public static WorkoutSlotResponse from(WorkoutExercise row) {
        return new WorkoutSlotResponse(
                row.getId(),
                row.getMovementPattern(),
                row.getExercise() == null,
                row.getExercise() == null ? null : ExerciseResponse.from(row.getExercise()),
                row.getSets(),
                row.getMinReps(),
                row.getMaxReps(),
                row.getTargetWeightKg()
        );
    }
}