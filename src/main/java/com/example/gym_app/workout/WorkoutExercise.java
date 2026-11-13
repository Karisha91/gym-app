package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.MovementPattern;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workout_exercises")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @Column(nullable = false)
    private int position;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_pattern", nullable = false)
    private MovementPattern movementPattern;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private int sets;

    @Column(name = "min_reps", nullable = false)
    private int minReps;

    @Column(name = "max_reps", nullable = false)
    private int maxReps;

    @Column(name = "target_weight_kg")
    private Double targetWeightKg;

    public WorkoutExercise(Workout workout, int position, MovementPattern movementPattern,
                           Exercise exercise, int sets, int minReps, int maxReps, Double targetWeightKg) {
        this.workout = workout;
        this.position = position;
        this.movementPattern = movementPattern;
        this.exercise = exercise;
        this.sets = sets;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.targetWeightKg = targetWeightKg;
    }
}