package com.example.gym_app.ai;

import java.util.List;

public record ExplainResponse(
        List<ResponseSlot> slots
) {
    public record ResponseSlot(
            int position,
            String why
    ) {}
}