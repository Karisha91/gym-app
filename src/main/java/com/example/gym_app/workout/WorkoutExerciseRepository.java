package com.example.gym_app.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {


    Optional<WorkoutExercise> findByIdAndWorkoutIdAndWorkoutUserId(
            Long id,
            Long workoutId,
            Long userId
    );
}