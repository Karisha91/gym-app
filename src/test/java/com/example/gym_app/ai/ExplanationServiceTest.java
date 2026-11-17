package com.example.gym_app.ai;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplanationServiceTest {

    @Test
    void returnsAiResponseWhenValid() {
        WorkoutExplainer validExplainer = req -> new ExplainResponse(
                req.slots().stream()
                        .map(s -> new ExplainResponse.ResponseSlot(s.position(), "Valid rationale."))
                        .toList()
        );
        ExplanationService service = new ExplanationService(validExplainer);

        ExplainResponse response = service.explain(request());

        assertEquals(2, response.slots().size());
        assertEquals("Valid rationale.", response.slots().get(0).why());
    }

    @Test
    void returnsFallbackWhenAiResponseIsInvalid() {
        WorkoutExplainer invalidExplainer = req -> new ExplainResponse(List.of(
                new ExplainResponse.ResponseSlot(1, "Bump to 25 kg."),
                new ExplainResponse.ResponseSlot(2, "Valid rationale.")
        ));
        ExplanationService service = new ExplanationService(invalidExplainer);

        ExplainResponse response = service.explain(request());

        assertEquals(ExplainValidator.fallbackFor(request()), response);
    }

    @Test
    void returnsFallbackWhenAiThrows() {
        WorkoutExplainer throwingExplainer = req -> {
            throw new RuntimeException("AI down");
        };
        ExplanationService service = new ExplanationService(throwingExplainer);

        ExplainResponse response = service.explain(request());

        assertEquals(ExplainValidator.fallbackFor(request()), response);
    }

    private static ExplainRequest request() {
        return new ExplainRequest(
                Goal.MUSCLE,
                Set.of(Equipment.DUMBBELL),
                List.of(
                        new ExplainRequest.RequestSlot(1, "Goblet Squat", MovementPattern.SQUAT),
                        new ExplainRequest.RequestSlot(2, "Dumbbell Bench Press", MovementPattern.PUSH)
                )
        );
    }
}