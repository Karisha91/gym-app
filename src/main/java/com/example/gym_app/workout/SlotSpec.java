package com.example.gym_app.workout;

import com.example.gym_app.exercise.MovementPattern;

public record SlotSpec(MovementPattern pattern, CompoundRule compoundRule) {}
