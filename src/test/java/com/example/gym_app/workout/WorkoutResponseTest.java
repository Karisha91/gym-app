package com.example.gym_app.workout;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutResponseTest {

    @Test
    void mapsWorkoutAndRowsInOrder() {
        Workout workout = new Workout(1L, Goal.MUSCLE, 60);

        Exercise squat = new Exercise();
        squat.setName("Goblet Squat");
        squat.setMovementPattern(MovementPattern.SQUAT);
        squat.setEquipment(Equipment.DUMBBELL);
        squat.setCompound(true);

        WorkoutExercise row1 = new WorkoutExercise(
                workout, 1, MovementPattern.SQUAT, squat, 3, 8, 12
        );
        WorkoutExercise row2 = new WorkoutExercise(
                workout, 2, MovementPattern.PULL, null, 3, 8, 12
        );

        WorkoutResponse response = WorkoutResponse.from(workout, List.of(row1, row2));

        assertEquals(workout.getCreatedAt(), response.createdAt());
        assertEquals(2, response.slots().size());
        assertFalse(response.slots().get(0).skipped());
        assertEquals("Goblet Squat", response.slots().get(0).exercise().name());
        assertTrue(response.slots().get(1).skipped());
    }
}