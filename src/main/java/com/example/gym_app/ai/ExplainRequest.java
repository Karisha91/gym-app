package com.example.gym_app.ai;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;

import java.util.List;
import java.util.Set;

public record ExplainRequest(
        Goal goal,
        Set<Equipment> equipment,
        List<RequestSlot> slots
) {
    public record RequestSlot(
            int position,
            String exerciseName,
            MovementPattern pattern
    ) {}
}