package com.example.gym_app.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatResponseReaderTest {

    @Test
    void extractsContentFromNormalEnvelope() {

        String responseBody = """
                {
                  "id": "chatcmpl-123",
                  "model": "liquid/lfm-3-1.7b:free",
                  "usage": {"prompt_tokens": 10, "completion_tokens": 20},
                  "choices": [
                    {
                      "index": 0,
                      "message": {
                        "role": "assistant",
                        "content": "{\\"slots\\": [{\\"position\\": 1, \\"why\\": \\"Good for legs.\\"}]}"
                      }
                    }
                  ]
                }
                """;

        String content = ChatResponseReader.extractContent(responseBody);

        assertNotNull(content);
        assertTrue(content.contains("Good for legs."));
    }

    @Test
    void throwsOnEmptyChoicesList() {
        String responseBody = """
                {
                  "id": "chatcmpl-123",
                  "choices": []
                }
                """;

        assertThrows(RuntimeException.class, () -> ChatResponseReader.extractContent(responseBody));
    }

    @Test
    void throwsOnNullContent() {
        String responseBody = """
                {
                  "choices": [
                    {
                      "message": {
                        "role": "assistant",
                        "content": null
                      }
                    }
                  ]
                }
                """;

        assertThrows(RuntimeException.class, () -> ChatResponseReader.extractContent(responseBody));
    }

    @Test
    void throwsOnErrorBody() {
        String responseBody = """
                {
                  "error": {
                    "message": "Rate limit exceeded",
                    "type": "rate_limit_error"
                  }
                }
                """;

        assertThrows(RuntimeException.class, () -> ChatResponseReader.extractContent(responseBody));
    }
}