package com.example.gym_app.workout;

import com.example.gym_app.profile.Goal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "workouts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Goal goal;

    @Column(name = "session_minutes", nullable = false)
    private int sessionMinutes;

    public Workout(Long userId, Goal goal, int sessionMinutes) {
        this.userId = userId;
        this.goal = goal;
        this.sessionMinutes = sessionMinutes;
        this.createdAt = Instant.now();
    }
}