package com.example.gym_app.profile;

import com.example.gym_app.exercise.Equipment;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profiles")
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false)
    private ExperienceLevel experienceLevel;

    @Column(name = "days_per_week", nullable = false)
    private int daysPerWeek;

    @Column(name = "session_minutes", nullable = false)
    private int sessionMinutes;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();


    @ElementCollection
    @CollectionTable(name = "profile_equipment", joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment", nullable = false)
    private Set<Equipment> equipment = new HashSet<>();

    protected Profile() {}

    public Profile(Long userId) {
        this.userId = userId;
        this.experienceLevel = ExperienceLevel.BEGINNER;
    }

    public void apply(Goal goal, int daysPerWeek, int sessionMinutes, Set<Equipment> equipment) {
        this.goal = goal;
        this.daysPerWeek = daysPerWeek;
        this.sessionMinutes = sessionMinutes;
        this.equipment.clear();
        this.equipment.addAll(equipment);
        this.updatedAt = Instant.now();
    }


}