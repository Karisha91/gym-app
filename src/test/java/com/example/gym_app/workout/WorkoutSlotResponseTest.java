package com.example.gym_app.workout;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutSlotResponseTest {

    @Test
    void filledRowMapsCorrectly() {
        Workout workout = new Workout(1L, Goal.MUSCLE, 60);

        Exercise exercise = new Exercise();
        exercise.setName("Goblet Squat");
        exercise.setMovementPattern(MovementPattern.SQUAT);
        exercise.setEquipment(Equipment.DUMBBELL);
        exercise.setCompound(true);

        WorkoutExercise row = new WorkoutExercise(
                workout, 1, MovementPattern.SQUAT, exercise, 3, 8, 12
        );

        WorkoutSlotResponse response = WorkoutSlotResponse.from(row);

        assertFalse(response.skipped());
        assertEquals(MovementPattern.SQUAT, response.pattern());
        assertNotNull(response.exercise());
        assertEquals("Goblet Squat", response.exercise().name());
        assertEquals(3, response.sets());
        assertEquals(8, response.minReps());
        assertEquals(12, response.maxReps());
    }

    @Test
    void skippedRowHasNullExercise() {
        Workout workout = new Workout(1L, Goal.MUSCLE, 60);

        WorkoutExercise row = new WorkoutExercise(
                workout, 2, MovementPattern.PULL, null, 3, 8, 12
        );

        WorkoutSlotResponse response = WorkoutSlotResponse.from(row);

        assertTrue(response.skipped());
        assertEquals(MovementPattern.PULL, response.pattern());
        assertNull(response.exercise());
        assertEquals(3, response.sets());
    }
}