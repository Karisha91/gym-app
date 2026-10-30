package com.example.gym_app.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_pattern", nullable = false)
    private MovementPattern movementPattern;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment", nullable = false)
    private Equipment equipment;

    @Column(name = "is_compound", nullable = false)
    private boolean compound;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;
}