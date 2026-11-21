package com.example.gym_app.ai;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChatRequestBuilderTest {

    private static final String MODEL = "liquid/lfm-3-1.7b:free";

    @Test
    void buildsExactlyTwoMessagesInOrder() {
        ChatRequest request = ChatRequestBuilder.build(MODEL, request());

        assertEquals(2, request.messages().size());
        assertEquals("system", request.messages().get(0).role());
        assertEquals("user", request.messages().get(1).role());
    }

    @Test
    void systemContentContainsKeyRules() {
        ChatRequest request = ChatRequestBuilder.build(MODEL, request());

        String system = request.messages().get(0).content();
        assertTrue(system.contains("150"), "System prompt should contain the 150 char limit");
        assertTrue(system.contains("JSON"), "System prompt should contain the JSON instruction");
        assertTrue(system.contains("digits"), "System prompt should contain the no-digits rule");
    }

    @Test
    void userContentContainsExerciseNamesAndPositions() {
        ChatRequest request = ChatRequestBuilder.build(MODEL, request());

        String user = request.messages().get(1).content();
        assertTrue(user.contains("Goblet Squat"));
        assertTrue(user.contains("Dumbbell Bench Press"));
        assertTrue(user.contains("\"position\":1"));
        assertTrue(user.contains("\"position\":2"));
    }

    @Test
    void passesThroughModelFormatAndLimits() {
        ChatRequest request = ChatRequestBuilder.build(MODEL, request());

        assertEquals(MODEL, request.model());
        assertEquals("json_object", request.responseFormat().type());
        assertEquals(800, request.maxTokens());
        assertEquals(0.3, request.temperature());
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