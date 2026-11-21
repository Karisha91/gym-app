package com.example.gym_app.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatRequest(
        String model,
        List<Message> messages,
        @JsonProperty("response_format") ResponseFormat responseFormat,
        @JsonProperty("max_tokens") int maxTokens,
        double temperature
) {
    public record Message(String role, String content) {}

    public record ResponseFormat(String type) {}
}