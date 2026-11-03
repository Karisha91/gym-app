package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    private static final Map<Goal, SetScheme> COMPOUND_SCHEMES = Map.of(
            Goal.STRENGTH, new SetScheme(3, 5, 5),
            Goal.MUSCLE, new SetScheme(3, 8, 12),
            Goal.FAT_LOSS, new SetScheme(3, 12, 15)
    );

    private static final SetScheme FILLER_SCHEME = new SetScheme(2, 12, 15);


    public static SetScheme schemeFor(Goal goal, SlotSpec slot) {
        if (slot.compoundRule() == CompoundRule.COMPOUND_ONLY) {
            return COMPOUND_SCHEMES.get(goal);
        } else {
            return FILLER_SCHEME;
        }
    }




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

    public static boolean isValidExerciseForSlot(SlotSpec slotSpec, Exercise exercise) {
        if (slotSpec.pattern() != exercise.getMovementPattern()) {
            return false;
        }

        switch (slotSpec.compoundRule()) {
            case COMPOUND_ONLY:
                return exercise.isCompound();
            case ISOLATION_ONLY:
                return !exercise.isCompound();
            case ANY:
                return true;
            default:
                throw new IllegalArgumentException("Unknown compound rule: " + slotSpec.compoundRule());
        }
    }

    public static List<Exercise> findCandidatesForSlot(SlotSpec slotSpec, List<Exercise> availableExercises) {
        return availableExercises.stream()
                .filter(exercise -> isValidExerciseForSlot(slotSpec, exercise))
                .toList();
    }

    public static Optional<Exercise> pickFirstUnused(List<Exercise> candidates, List<Exercise> alreadyPicked) {
        return candidates.stream()
                .filter(exercise -> !alreadyPicked.contains(exercise))
                .findFirst();
    }

    public static PlannedSlot planSlot(SlotSpec slotSpec, List<Exercise> availableExercises, List<Exercise> alreadyPicked) {
        List<Exercise> candidates = findCandidatesForSlot(slotSpec, availableExercises);
        Optional<Exercise> pickedExercise = pickFirstUnused(candidates, alreadyPicked);
        return new PlannedSlot(slotSpec, pickedExercise);
    }

    public static List<PlannedSlot> planWorkout(int workoutMinutes, List<Exercise> availableExercises) {
        List<SlotSpec> slots = getSlotsForMinutes(workoutMinutes);
        List<Exercise> alreadyPicked = new ArrayList<>();
        List<PlannedSlot> plannedSlots = new ArrayList<>();

        for (SlotSpec slotSpec : slots) {
            PlannedSlot plannedSlot = planSlot(slotSpec, availableExercises, alreadyPicked);
            plannedSlots.add(plannedSlot);
            plannedSlot.exercise().ifPresent(alreadyPicked::add);
        }

        return plannedSlots;
    }

}
