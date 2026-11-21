package com.example.gym_app.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("openai")
public class OpenAiWorkoutExplainer implements WorkoutExplainer {

    private static final Logger log = LoggerFactory.getLogger(OpenAiWorkoutExplainer.class);

    private final OpenRouterProperties properties;
    private final RestClient restClient;

    public OpenAiWorkoutExplainer(OpenRouterProperties properties) {
        this.properties = properties;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.connectTimeout());
        factory.setReadTimeout(properties.readTimeout());

        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(factory)
                .build();
    }

    @Override
    public ExplainResponse explain(ExplainRequest request) {
        if (properties.apiKey() == null || properties.apiKey().isBlank()) {
            throw new IllegalStateException("OPENROUTER_API_KEY is missing or empty");
        }

        ChatRequest chatRequest = ChatRequestBuilder.build(properties.model(), request);

        String rawResponse = restClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + properties.apiKey())
                .body(chatRequest)
                .retrieve()
                .body(String.class);


        String aiContent = ChatResponseReader.extractContent(rawResponse);

        return ExplainResponseParser.parse(aiContent);
    }
}