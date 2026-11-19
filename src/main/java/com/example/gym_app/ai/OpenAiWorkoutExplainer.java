package com.example.gym_app.ai;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("openai")
public class OpenAiWorkoutExplainer implements WorkoutExplainer {

    @Override
    public ExplainResponse explain(ExplainRequest request) {
        throw new UnsupportedOperationException("Real OpenAI client not implemented yet");
    }
}