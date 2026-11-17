package com.example.gym_app.ai;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ExplainValidator {

    private static final int MAX_RATIONALE_LENGTH = 150;

    public static final String FALLBACK_WHY = "Chosen to balance your workout.";

    public static boolean isValid(ExplainRequest request, ExplainResponse response) {
        if (response == null || response.slots() == null) return false;
        if (request == null || request.slots() == null) return false;

        if (response.slots().size() != request.slots().size()) return false;


        if (response.slots().stream().anyMatch(Objects::isNull)) return false;
        if (request.slots().stream().anyMatch(Objects::isNull)) return false;

        Set<Integer> requestPositions = request.slots().stream()
                .map(ExplainRequest.RequestSlot::position)
                .collect(Collectors.toSet());

        Set<Integer> responsePositions = response.slots().stream()
                .map(ExplainResponse.ResponseSlot::position)
                .collect(Collectors.toSet());

        if (!requestPositions.equals(responsePositions)) return false;

        for (ExplainResponse.ResponseSlot slot : response.slots()) {
            String why = slot.why();
            if (why == null || why.isBlank()) return false;
            if (why.length() > MAX_RATIONALE_LENGTH) return false;
            if (why.chars().anyMatch(Character::isDigit)) return false;
        }

        return true;
    }

    public static ExplainResponse fallbackFor(ExplainRequest request) {
        List<ExplainResponse.ResponseSlot> slots = request.slots().stream()
                .map(rs -> new ExplainResponse.ResponseSlot(rs.position(), FALLBACK_WHY))
                .toList();
        return new ExplainResponse(slots);
    }
}