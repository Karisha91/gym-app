package com.example.gym_app.ai;

import tools.jackson.databind.json.JsonMapper;

public class ExplainResponseParser {

    private static final JsonMapper MAPPER = JsonMapper.builder().build();
    private static final String FENCE = "```";

    public static ExplainResponse parse(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("AI reply is empty");
        }

        String json = stripCodeFence(content.trim());

        ExplainResponse response = MAPPER.readValue(json, ExplainResponse.class);

        if (response == null || response.slots() == null) {
            throw new IllegalArgumentException("AI reply has no slots");
        }
        return response;
    }

    private static String stripCodeFence(String text) {
        if (!text.startsWith(FENCE)) {
            return text;
        }

        int firstLineEnd = text.indexOf('\n');
        if (firstLineEnd == -1) {
            throw new IllegalArgumentException("AI reply has an unterminated code fence");
        }

        String withoutOpening = text.substring(firstLineEnd + 1).trim();
        if (!withoutOpening.endsWith(FENCE)) {
            throw new IllegalArgumentException("AI reply has an unterminated code fence");
        }

        return withoutOpening.substring(0, withoutOpening.length() - FENCE.length()).trim();
    }
}