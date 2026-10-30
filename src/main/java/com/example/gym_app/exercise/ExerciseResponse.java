package com.example.gym_app.exercise;

public record ExerciseResponse(
        Long id,
        String name,
        MuscleGroup muscleGroup,
        MovementPattern movementPattern,
        Equipment equipment,
        boolean compound,
        String instructions
) {
    public static ExerciseResponse from(Exercise e) {
        return new ExerciseResponse(
                e.getId(),
                e.getName(),
                e.getMuscleGroup(),
                e.getMovementPattern(),
                e.getEquipment(),
                e.isCompound(),
                e.getInstructions()
        );
    }
}