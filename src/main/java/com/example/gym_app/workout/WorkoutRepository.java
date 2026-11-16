package com.example.gym_app.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findFirstByUserIdOrderByIdDesc(Long userId);
}