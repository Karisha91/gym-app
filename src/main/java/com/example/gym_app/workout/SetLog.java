package com.example.gym_app.workout;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "set_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @Column(name = "weight_kg", nullable = false)
    private double weightKg;

    @Column(nullable = false)
    private int reps;

    @Column(name = "logged_at", nullable = false)
    private Instant loggedAt;

    public SetLog(WorkoutExercise workoutExercise, int setNumber, double weightKg, int reps) {
        this.workoutExercise = workoutExercise;
        this.setNumber = setNumber;
        this.weightKg = weightKg;
        this.reps = reps;
        this.loggedAt = Instant.now();
    }
}