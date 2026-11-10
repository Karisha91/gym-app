package com.example.gym_app.workout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SetEntry(
        @NotNull @PositiveOrZero Double weightKg,
        @NotNull @Min(1) Integer reps
) {}