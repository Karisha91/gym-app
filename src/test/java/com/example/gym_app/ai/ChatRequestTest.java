package com.example.gym_app.ai;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatRequestTest {

    @Test
    void serializesWithSnakeCaseFields() {
        JsonMapper mapper = JsonMapper.builder().build();

        ChatRequest request = new ChatRequest(
                "liquid/lfm-3-1.7b:free",
                List.of(new ChatRequest.Message("system", "You are a coach.")),
                new ChatRequest.ResponseFormat("json_object"),
                100,
                0.7
        );

        String json = mapper.writeValueAsString(request);


        assertTrue(json.contains("\"max_tokens\""), "JSON should contain max_tokens");
        assertTrue(json.contains("\"response_format\""), "JSON should contain response_format");


        assertFalse(json.contains("maxTokens"), "JSON should not contain maxTokens");
        assertFalse(json.contains("responseFormat"), "JSON should not contain responseFormat");
    }
}