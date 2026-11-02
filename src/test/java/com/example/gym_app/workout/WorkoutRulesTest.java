package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutRulesTest {

    @Test
    void thirtyMinutesGivesFourExercises() {
        assertEquals(4, WorkoutRules.getExerciseCount(30));
    }

    @Test
    void fiftyMinutesThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            WorkoutRules.getExerciseCount(50);
        });
    }

    @Test
    void thirtyMinutesGivesFourSlotsStartingWithSquat() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(30);
        assertEquals(4, slots.size());
        assertEquals(new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY), slots.getFirst());

    }

    @Test
    void fortyFiveMinutesGivesFiveExercises() {
        assertEquals(5, WorkoutRules.getExerciseCount(45));
    }

    @Test
    void fortyFiveMinutesGivesFiveSlotsLastOneCore() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(45);
        assertEquals(5, slots.size());
        assertEquals(new SlotSpec(MovementPattern.CORE, CompoundRule.ANY), slots.getLast());
    }

    @Test
    void sixtyMinutesGivesSixExercises() {
        assertEquals(6, WorkoutRules.getExerciseCount(60));
    }

    @Test
    void sixtyMinutesGivesSixSlotsLastOnePull() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(60);
        assertEquals(6, slots.size());
        assertEquals(new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY), slots.getLast());
    }

    @Test
    void seventyFiveMinutesGivesSevenExercises() {
        assertEquals(7, WorkoutRules.getExerciseCount(75));
    }

    @Test
    void seventyFiveMinutesGivesSevenSlotsLastOnePushIsolation() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(75);
        assertEquals(7, slots.size());
        assertEquals(new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY), slots.getLast());
    }

    @Test
    void ninetyMinutesGivesEightExercises() {
        assertEquals(8, WorkoutRules.getExerciseCount(90));
    }

    @Test
    void ninetyMinutesGivesEightSlotsLastOneSquatIsolation() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(90);
        assertEquals(8, slots.size());
        assertEquals(new SlotSpec(MovementPattern.SQUAT, CompoundRule.ISOLATION_ONLY), slots.getLast());
    }

    @Test
    void ninetyMinutesGivesFullLadderInOrder() {
        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(90);
        assertEquals(List.of(
                new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY),
                new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY),
                new SlotSpec(MovementPattern.HINGE, CompoundRule.COMPOUND_ONLY),
                new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY),
                new SlotSpec(MovementPattern.CORE, CompoundRule.ANY),
                new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY),
                new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY),
                new SlotSpec(MovementPattern.SQUAT, CompoundRule.ISOLATION_ONLY)
        ), slots);
    }


    @Test
    void isValidExerciseForSlotReturnsTrueForMatchingPatternAndCompoundRule() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.SQUAT);
        exercise.setCompound(true);

        boolean result = WorkoutRules.isValidExerciseForSlot(slotSpec, exercise);
        assertEquals(true, result);
    }

    @Test
    void isValidExerciseForSlotReturnsFalseWhenPatternMismatches() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.PUSH);
        exercise.setCompound(true);

        assertFalse(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void isValidExerciseForSlotReturnsFalseWhenCompoundOnlyButExerciseIsIsolation() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.SQUAT);
        exercise.setCompound(false);

        assertFalse(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void isValidExerciseForSlotReturnsTrueForIsolationOnlyAndIsolationExercise() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.PUSH);
        exercise.setCompound(false);

        assertTrue(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void isValidExerciseForSlotReturnsFalseForIsolationOnlyButCompoundExercise() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.PUSH);
        exercise.setCompound(true);

        assertFalse(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void isValidExerciseForSlotReturnsTrueForAnyRuleWithCompoundExercise() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.CORE, CompoundRule.ANY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.CORE);
        exercise.setCompound(true);

        assertTrue(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void isValidExerciseForSlotReturnsTrueForAnyRuleWithIsolationExercise() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.CORE, CompoundRule.ANY);
        Exercise exercise = new Exercise();
        exercise.setMovementPattern(MovementPattern.CORE);
        exercise.setCompound(false);

        assertTrue(WorkoutRules.isValidExerciseForSlot(slotSpec, exercise));
    }

    @Test
    void findCandidatesForSlotReturnsOnlyValidExercises() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        Exercise validExercise = new Exercise();
        validExercise.setMovementPattern(MovementPattern.SQUAT);
        validExercise.setCompound(true);

        Exercise invalidExercise1 = new Exercise();
        invalidExercise1.setMovementPattern(MovementPattern.PUSH);
        invalidExercise1.setCompound(true);

        Exercise invalidExercise2 = new Exercise();
        invalidExercise2.setMovementPattern(MovementPattern.SQUAT);
        invalidExercise2.setCompound(false);

        List<Exercise> availableExercises = List.of(validExercise, invalidExercise1, invalidExercise2);
        List<Exercise> candidates = WorkoutRules.findCandidatesForSlot(slotSpec, availableExercises);

        assertEquals(1, candidates.size());
        assertTrue(candidates.contains(validExercise));
    }

    @Test
    void findCandidatesForSlotReturnsEmptyListWhenNoValidExercises() {
        SlotSpec slotSpec = new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY);
        Exercise invalidExercise1 = new Exercise();
        invalidExercise1.setMovementPattern(MovementPattern.SQUAT);
        invalidExercise1.setCompound(true);

        Exercise invalidExercise2 = new Exercise();
        invalidExercise2.setMovementPattern(MovementPattern.PUSH);
        invalidExercise2.setCompound(true);

        List<Exercise> availableExercises = List.of(invalidExercise1, invalidExercise2);
        List<Exercise> candidates = WorkoutRules.findCandidatesForSlot(slotSpec, availableExercises);

        assertTrue(candidates.isEmpty());
    }


    @Test
    void pickExerciseSkipsAlreadyPicked() {
        Exercise a = new Exercise();
        a.setMovementPattern(MovementPattern.PULL);
        a.setCompound(true);

        Exercise b = new Exercise();
        b.setMovementPattern(MovementPattern.PULL);
        b.setCompound(true);

        Optional<Exercise> result = WorkoutRules.pickFirstUnused(List.of(a, b), List.of(a));

        assertEquals(Optional.of(b), result);
    }

    @Test
    void pickExerciseReturnsEmptyWhenAllPicked() {
        Exercise a = new Exercise();
        a.setMovementPattern(MovementPattern.PULL);
        a.setCompound(true);

        Optional<Exercise> result = WorkoutRules.pickFirstUnused(List.of(a), List.of(a));

        assertTrue(result.isEmpty());
    }

    @Test
    void plannedSlotWithExerciseIsNotSkipped() {
        Exercise exercise = new Exercise();
        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        PlannedSlot planned = new PlannedSlot(slot, Optional.of(exercise));

        assertFalse(planned.isSkipped());
    }

    @Test
    void plannedSlotWithEmptyExerciseIsSkipped() {
        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        PlannedSlot planned = new PlannedSlot(slot, Optional.empty());

        assertTrue(planned.isSkipped());
    }

    @Test
    void planSlotReturnsFilledWhenExerciseMatches() {
        Exercise squat = new Exercise();
        squat.setMovementPattern(MovementPattern.SQUAT);
        squat.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(squat), List.of());

        assertFalse(planned.isSkipped());
        assertEquals(Optional.of(squat), planned.exercise());
    }

    @Test
    void planSlotReturnsSkippedWhenNothingMatches() {
        Exercise push = new Exercise();
        push.setMovementPattern(MovementPattern.PUSH);
        push.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(push), List.of());

        assertTrue(planned.isSkipped());
    }

    @Test
    void planSlotReturnsSkippedWhenOnlyMatchAlreadyPicked() {
        Exercise row = new Exercise();
        row.setMovementPattern(MovementPattern.PULL);
        row.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(row), List.of(row));

        assertTrue(planned.isSkipped());
    }

    @Test
    void getTheLadderForFortyFiveMinutes() {

        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(45);


        Exercise gobletSquat = new Exercise();
        gobletSquat.setName("Goblet Squat");
        gobletSquat.setMovementPattern(MovementPattern.SQUAT);
        gobletSquat.setCompound(true);


        Exercise legExtension = new Exercise();
        legExtension.setName("Leg Extension");
        legExtension.setMovementPattern(MovementPattern.SQUAT);
        legExtension.setCompound(false);


        Exercise pushUp = new Exercise();
        pushUp.setName("Push-Up");
        pushUp.setMovementPattern(MovementPattern.PUSH);
        pushUp.setCompound(true);


        Exercise dumbbellRow = new Exercise();
        dumbbellRow.setName("Dumbbell Row");
        dumbbellRow.setMovementPattern(MovementPattern.PULL);
        dumbbellRow.setCompound(true);


        Exercise plank = new Exercise();
        plank.setName("Plank");
        plank.setMovementPattern(MovementPattern.CORE);
        plank.setCompound(false);


        List<Exercise> catalog = List.of(gobletSquat, legExtension, pushUp, dumbbellRow, plank);

        List<Exercise> alreadyPicked = new ArrayList<>();
        List<PlannedSlot> plannedWorkout = new ArrayList<>();

        for (SlotSpec slot : slots) {
            PlannedSlot planned = WorkoutRules.planSlot(slot, catalog, alreadyPicked);
            plannedWorkout.add(planned);
            planned.exercise().ifPresent(alreadyPicked::add);
        }

        assertEquals(5, plannedWorkout.size());
        assertEquals("Goblet Squat", plannedWorkout.get(0).exercise().get().getName());
        assertEquals("Push-Up", plannedWorkout.get(1).exercise().get().getName());
        assertTrue(plannedWorkout.get(2).isSkipped());
        assertEquals("Dumbbell Row", plannedWorkout.get(3).exercise().get().getName());
        assertEquals("Plank", plannedWorkout.get(4).exercise().get().getName());



    }


}