package com.example.gym_app.workout;

import com.example.gym_app.exercise.MovementPattern;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}