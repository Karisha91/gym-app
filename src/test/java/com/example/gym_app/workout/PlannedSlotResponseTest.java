package com.example.gym_app.workout;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlannedSlotResponseTest {

    @Test
    void filledSlotHasExerciseAndIsNotSkipped() {

        Exercise gobletSquat = new Exercise();
        gobletSquat.setId(1L);
        gobletSquat.setName("Goblet Squat");
        gobletSquat.setMovementPattern(MovementPattern.SQUAT);
        gobletSquat.setEquipment(Equipment.DUMBBELL);
        gobletSquat.setCompound(true);
        gobletSquat.setInstructions("...");


        SlotSpec slot = new SlotSpec(MovementPattern.SQUAT, CompoundRule.COMPOUND_ONLY);
        PlannedSlot planned = new PlannedSlot(
                slot,
                Optional.of(gobletSquat),
                new SetScheme(3, 8, 12)
        );


        PlannedSlotResponse response = PlannedSlotResponse.from(planned);


        assertFalse(response.skipped());
        assertEquals(MovementPattern.SQUAT, response.pattern());
        assertNotNull(response.exercise());
        assertEquals("Goblet Squat", response.exercise().name());
        assertEquals(3, response.sets());
        assertEquals(8, response.minReps());
        assertEquals(12, response.maxReps());
    }

    @Test
    void skippedSlotHasNullExerciseAndStillCarriesScheme() {
        SlotSpec slot = new SlotSpec(MovementPattern.PULL, CompoundRule.COMPOUND_ONLY);
        PlannedSlot planned = new PlannedSlot(
                slot,
                Optional.empty(),
                new SetScheme(3, 8, 12)
        );


        PlannedSlotResponse response = PlannedSlotResponse.from(planned);


        assertTrue(response.skipped());
        assertEquals(MovementPattern.PULL, response.pattern());
        assertNull(response.exercise());
        assertEquals(3, response.sets());
        assertEquals(8, response.minReps());
        assertEquals(12, response.maxReps());
    }
}