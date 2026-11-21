package com.example.gym_app.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExplainResponseParserTest {

    @Test
    void parsesCleanJson() {
        String rawContent = "{\"slots\": [{\"position\": 1, \"why\": \"Good for legs.\"}]}";

        ExplainResponse response = ExplainResponseParser.parse(rawContent);

        assertNotNull(response);
        assertEquals(1, response.slots().size());
        assertEquals(1, response.slots().get(0).position());
        assertEquals("Good for legs.", response.slots().get(0).why());
    }

    @Test
    void parsesJsonInsideCodeFence() {
        String rawContent = """
                ```json
                {"slots": [{"position": 1, "why": "Good for legs."}]}
                ```
                """;

        ExplainResponse response = ExplainResponseParser.parse(rawContent);

        assertNotNull(response);
        assertEquals(1, response.slots().size());
        assertEquals(1, response.slots().get(0).position());
        assertEquals("Good for legs.", response.slots().get(0).why());
    }

    @Test
    void throwsOnGarbageText() {
        String rawContent = "sorry, I can't do that";
        assertThrows(RuntimeException.class, () -> ExplainResponseParser.parse(rawContent));
    }

    @Test
    void throwsOnWrongJsonShape() {
        String rawContent = "{\"hello\": 1}";
        assertThrows(RuntimeException.class, () -> ExplainResponseParser.parse(rawContent));
    }

    @Test
    void throwsOnNullInput() {
        assertThrows(RuntimeException.class, () -> ExplainResponseParser.parse(null));
    }

    @Test
    void throwsOnFencedReplyWithoutClosingFence() {
        String rawContent = """
                ```json
                {"slots": [{"position": 1, "why": "Good for legs."}]}
                """;
        assertThrows(RuntimeException.class, () -> ExplainResponseParser.parse(rawContent));
    }
}