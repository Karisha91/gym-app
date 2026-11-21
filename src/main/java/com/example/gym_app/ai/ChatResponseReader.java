package com.example.gym_app.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

public class ChatResponseReader {

    private static final JsonMapper MAPPER = JsonMapper.builder().build();

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ChatResponse(List<Choice> choices, ErrorBody error) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Choice(Message message) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Message(String content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ErrorBody(String message) {}

    public static String extractContent(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            throw new IllegalArgumentException("AI reply body is empty");
        }

        ChatResponse response = MAPPER.readValue(responseBody, ChatResponse.class);
        if (response == null) {
            throw new IllegalArgumentException("AI reply body is not an object");
        }

        if (response.choices() == null || response.choices().isEmpty()) {
            String reason = (response.error() != null && response.error().message() != null)
                    ? response.error().message()
                    : "no choices";
            throw new IllegalArgumentException("AI reply has no choices: " + reason);
        }

        Choice first = response.choices().get(0);
        if (first == null || first.message() == null) {
            throw new IllegalArgumentException("AI reply has no message");
        }

        String content = first.message().content();
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("AI reply content is empty");
        }

        return content;
    }
}