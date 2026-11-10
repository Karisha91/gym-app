package com.example.gym_app.workout;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record LogSetsRequest(
        @NotNull
        @Size(min = 1, max = 10)
        List<@NotNull @Valid SetEntry> sets
) {}