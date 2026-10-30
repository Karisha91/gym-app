package com.example.gym_app.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public List<ExerciseResponse> findAll() {
        return exerciseRepository.findAll().stream()
                .map(ExerciseResponse::from)
                .toList();
    }
}