package com.example.gym_app.workout;

import com.example.gym_app.ai.ExplainRequest;
import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplainRequestBuilderTest {

    @Test
    void skippedSlotIsExcludedFromRequest() {

        Exercise squat = new Exercise();
        squat.setName("Goblet Squat");
        PlannedSlot slot1 = new PlannedSlot(
                new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY),
                Optional.of(squat),
                new SetScheme(3, 8, 12)
        );


        PlannedSlot slot2 = new PlannedSlot(
                new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY),
                Optional.empty(),
                new SetScheme(3, 8, 12)
        );


        Exercise push = new Exercise();
        push.setName("Push-Up");
        PlannedSlot slot3 = new PlannedSlot(
                new SlotSpec(MovementPattern.PUSH, CompoundRule.COMPOUND_ONLY),
                Optional.of(push),
                new SetScheme(3, 8, 12)
        );

        List<PlannedSlot> plannedSlots = List.of(slot1, slot2, slot3);

        ExplainRequest request = ExplainRequestBuilder.build(
                Goal.MUSCLE,
                Set.of(Equipment.DUMBBELL),
                plannedSlots
        );


        assertEquals(2, request.slots().size());
        assertEquals(1, request.slots().get(0).position());
        assertEquals("Goblet Squat", request.slots().get(0).exerciseName());
        assertEquals(3, request.slots().get(1).position());
        assertEquals("Push-Up", request.slots().get(1).exerciseName());
    }
}