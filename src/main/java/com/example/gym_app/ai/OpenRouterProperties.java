package com.example.gym_app.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "openrouter")
public record OpenRouterProperties(
        String apiKey,
        String baseUrl,
        String model,
        Duration connectTimeout,
        Duration readTimeout
) {}