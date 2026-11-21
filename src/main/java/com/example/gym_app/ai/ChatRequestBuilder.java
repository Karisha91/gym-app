package com.example.gym_app.ai;

import tools.jackson.databind.json.JsonMapper;

import java.util.List;

public class ChatRequestBuilder {

    private static final JsonMapper MAPPER = JsonMapper.builder().build();

    private static final int MAX_TOKENS = 800;
    private static final double TEMPERATURE = 0.3;

    private static final String SYSTEM_PROMPT = """
            You write short explanations for a beginner gym workout app.
            
            For each exercise you are given, write ONE sentence that explains why that exercise suits the person's goal and equipment.
            
            Rules:
            1. Exactly one sentence per exercise, 150 characters or fewer.
            2. Do not use any digits. No numbers at all: no weights, reps, sets, days or weeks. Write words instead of numbers if you must.
            3. Only talk about the exercise named in that slot. Never mention other exercises.
            4. Do not make claims about the person's history, progress or past sessions. You know only their goal and equipment.
            5. Do not give medical or injury advice.
            6. Vary the wording between exercises. Do not start two sentences the same way.
            7. Return only JSON in exactly this shape, with no text before or after it:
               {"slots": [{"position": 1, "why": "..."}]}
               Return one entry for every slot you receive, with the same position numbers, no extras, no duplicates.
            """;

    public static ChatRequest build(String model, ExplainRequest request) {
        String userJson = MAPPER.writeValueAsString(request);

        return new ChatRequest(
                model,
                List.of(
                        new ChatRequest.Message("system", SYSTEM_PROMPT),
                        new ChatRequest.Message("user", userJson)
                ),
                new ChatRequest.ResponseFormat("json_object"),
                MAX_TOKENS,
                TEMPERATURE
        );
    }
}