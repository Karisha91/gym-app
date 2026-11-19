package com.example.gym_app.ai;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExplanationService {

    private static final Logger log = LoggerFactory.getLogger(ExplanationService.class);

    private final WorkoutExplainer explainer;

    public ExplainResponse explain(ExplainRequest request) {

        if (request.slots().isEmpty()) {
            return ExplainValidator.fallbackFor(request);
        }

        try {
            ExplainResponse response = explainer.explain(request);
            if (ExplainValidator.isValid(request, response)) {
                return response;
            }
            log.warn("AI response failed validation; using fallback");
        } catch (RuntimeException ex) {
            log.warn("AI call failed; using fallback", ex);
        }
        return ExplainValidator.fallbackFor(request);
    }
}