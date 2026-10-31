package com.example.gym_app.workout;

import com.example.gym_app.exercise.MovementPattern;

import java.util.List;
import java.util.Map;

public class WorkoutRules {


    private static final Map<Integer, Integer> WORKOUT_MINUTES_TO_EXERCISE_COUNT = Map.of(
            30, 4,
            45, 5,
            60, 6,
            75, 7,
            90, 8
    );

    private static final List<SlotSpec> SLOTS = List.of(
            new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY),
            new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY),
            new SlotSpec(MovementPattern.HINGE, CompoundRule.COMPOUND_ONLY),
            new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY),
            new SlotSpec(MovementPattern.CORE, CompoundRule.ANY),
            new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY),
            new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY),
            new SlotSpec(MovementPattern.SQUAT, CompoundRule.ISOLATION_ONLY)
    );


    public static int getExerciseCount(int workoutMinutes) {
        Integer exerciseCount = WORKOUT_MINUTES_TO_EXERCISE_COUNT.get(workoutMinutes);
        if (exerciseCount == null) {
            throw new IllegalArgumentException("Invalid workout minutes: " + workoutMinutes);
        }
        return exerciseCount;
    }


    public static List<SlotSpec> getSlotsForMinutes(int workoutMinutes) {
        int count = getExerciseCount(workoutMinutes);
        return SLOTS.subList(0, count);
    }

}
