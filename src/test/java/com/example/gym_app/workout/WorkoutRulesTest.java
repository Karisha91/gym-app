package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
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
        assertTrue(result);
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
        PlannedSlot planned = new PlannedSlot(
                slot,
                Optional.of(exercise),
                new SetScheme(3, 8, 12)
        );

        assertFalse(planned.isSkipped());
    }

    @Test
    void plannedSlotWithEmptyExerciseIsSkipped() {
        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        PlannedSlot planned = new PlannedSlot(
                slot,
                Optional.empty(),
                new SetScheme(3, 8, 12)   // placeholder
        );

        assertTrue(planned.isSkipped());
    }

    @Test
    void planSlotReturnsFilledWhenExerciseMatches() {
        Exercise squat = new Exercise();
        squat.setMovementPattern(MovementPattern.SQUAT);
        squat.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(squat), List.of(), Goal.MUSCLE);

        assertFalse(planned.isSkipped());
        assertEquals(Optional.of(squat), planned.exercise());
    }

    @Test
    void planSlotReturnsSkippedWhenNothingMatches() {
        Exercise push = new Exercise();
        push.setMovementPattern(MovementPattern.PUSH);
        push.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(push), List.of(), Goal.MUSCLE);

        assertTrue(planned.isSkipped());
    }

    @Test
    void planSlotReturnsSkippedWhenOnlyMatchAlreadyPicked() {
        Exercise row = new Exercise();
        row.setMovementPattern(MovementPattern.PULL);
        row.setCompound(true);

        SlotSpec slot = new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY);

        PlannedSlot planned = WorkoutRules.planSlot(slot, List.of(row), List.of(row), Goal.MUSCLE);

        assertTrue(planned.isSkipped());
    }

    @Test
    void getTheLadderForFortyFiveMinutes() {


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

        List<PlannedSlot> plannedWorkout = WorkoutRules.planWorkout(45, catalog, Goal.MUSCLE);

        assertEquals(5, plannedWorkout.size());
        assertEquals("Goblet Squat", plannedWorkout.get(0).exercise().get().getName());
        assertEquals(new SetScheme(3, 8, 12), plannedWorkout.get(0).scheme());   // NEW assertion
        assertEquals("Push-Up", plannedWorkout.get(1).exercise().get().getName());
        assertTrue(plannedWorkout.get(2).isSkipped());
        assertEquals("Dumbbell Row", plannedWorkout.get(3).exercise().get().getName());
        assertEquals("Plank", plannedWorkout.get(4).exercise().get().getName());
    }

    @Test
    void sixtyMinutesGivesSixSlotsWithSecondPull() {

        List<SlotSpec> slots = WorkoutRules.getSlotsForMinutes(60);
        assertEquals(6, slots.size());
        assertEquals(new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY), slots.getLast());


        Exercise squat = new Exercise();
        squat.setName("Squat");
        squat.setMovementPattern(MovementPattern.SQUAT);
        squat.setCompound(true);

        Exercise push = new Exercise();
        push.setName("Push-Up");
        push.setMovementPattern(MovementPattern.PUSH);
        push.setCompound(true);

        Exercise hinge = new Exercise();
        hinge.setName("Glute Bridge");
        hinge.setMovementPattern(MovementPattern.HINGE);
        hinge.setCompound(true);

        Exercise pull1 = new Exercise();
        pull1.setName("Dumbbell Row");
        pull1.setMovementPattern(MovementPattern.PULL);
        pull1.setCompound(true);

        Exercise pull2 = new Exercise();
        pull2.setName("Lat Pulldown");
        pull2.setMovementPattern(MovementPattern.PULL);
        pull2.setCompound(true);

        Exercise core = new Exercise();
        core.setName("Plank");
        core.setMovementPattern(MovementPattern.CORE);
        core.setCompound(false);

        List<Exercise> catalog = List.of(squat, push, hinge, pull1, pull2, core);


        List<PlannedSlot> plannedWorkout = WorkoutRules.planWorkout(60, catalog, Goal.MUSCLE);


        assertEquals(6, plannedWorkout.size());


        assertEquals("Dumbbell Row", plannedWorkout.get(3).exercise().get().getName());


        assertEquals("Lat Pulldown", plannedWorkout.get(5).exercise().get().getName());
    }

    @Test
    void sixtyMinutesWithOnlyOnePullSkipsSecondPull() {
        List<Exercise> catalog = List.of(
                exercise("Squat",         MovementPattern.SQUAT, true),
                exercise("Push-Up",       MovementPattern.PUSH,  true),
                exercise("Glute Bridge",  MovementPattern.HINGE, true),
                exercise("Dumbbell Row",  MovementPattern.PULL,  true),
                exercise("Plank",         MovementPattern.CORE,  false)
        );

        List<PlannedSlot> plannedWorkout = WorkoutRules.planWorkout(60, catalog, Goal.MUSCLE);

        assertEquals("Dumbbell Row", plannedWorkout.get(3).exercise().get().getName());
        assertTrue(plannedWorkout.get(5).isSkipped());
    }


    private static Exercise exercise(String name, MovementPattern pattern, boolean compound) {
        Exercise e = new Exercise();
        e.setName(name);
        e.setMovementPattern(pattern);
        e.setCompound(compound);
        return e;
    }

    @Test
    void schemeForCompoundOnlyStrength() {
        SlotSpec slot = new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY);
        assertEquals(new SetScheme(3, 5, 5), WorkoutRules.schemeFor(Goal.STRENGTH, slot));
    }

    @Test
    void schemeForCompoundOnlyMuscle() {
        SlotSpec slot = new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY);
        assertEquals(new SetScheme(3, 8, 12), WorkoutRules.schemeFor(Goal.MUSCLE, slot));
    }

    @Test
    void schemeForCompoundOnlyFatLoss() {
        SlotSpec slot = new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY);
        assertEquals(new SetScheme(3, 12, 15), WorkoutRules.schemeFor(Goal.FAT_LOSS, slot));
    }

    @Test
    void schemeForIsolationOnlyGivesFiller() {
        SlotSpec slot = new SlotSpec(MovementPattern.PUSH, CompoundRule.ISOLATION_ONLY);
        assertEquals(new SetScheme(2, 12, 15), WorkoutRules.schemeFor(Goal.STRENGTH, slot));
    }

    @Test
    void schemeForCoreWithAnyGivesFiller() {
        SlotSpec slot = new SlotSpec(MovementPattern.CORE, CompoundRule.ANY);
        assertEquals(new SetScheme(2, 12, 15), WorkoutRules.schemeFor(Goal.MUSCLE, slot));
    }

    @Test
    void schemeForEveryGoalIsDefined() {
        SlotSpec compoundSlot = new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY);
        for (Goal goal : Goal.values()) {
            assertNotNull(WorkoutRules.schemeFor(goal, compoundSlot),
                    "No scheme defined for goal " + goal);
        }
    }
}