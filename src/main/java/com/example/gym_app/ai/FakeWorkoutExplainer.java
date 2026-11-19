package com.example.gym_app.ai;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!openai")
public class FakeWorkoutExplainer implements WorkoutExplainer {

    @Override
    public ExplainResponse explain(ExplainRequest request) {
        List<ExplainResponse.ResponseSlot> slots = request.slots().stream()
                .map(slot -> new ExplainResponse.ResponseSlot(
                        slot.position(),
                        slot.exerciseName() + " is a good fit for your goal."
                ))
                .toList();
        return new ExplainResponse(slots);
    }
}