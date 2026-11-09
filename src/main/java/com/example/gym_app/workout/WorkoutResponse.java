package com.example.gym_app.workout;

import java.time.Instant;
import java.util.List;

public record WorkoutResponse(
        Long id,
        Instant createdAt,
        List<WorkoutSlotResponse> slots
) {
    public static WorkoutResponse from(Workout workout, List<WorkoutExercise> rows) {
        return new WorkoutResponse(
                workout.getId(),
                workout.getCreatedAt(),
                rows.stream().map(WorkoutSlotResponse::from).toList()
        );
    }
}