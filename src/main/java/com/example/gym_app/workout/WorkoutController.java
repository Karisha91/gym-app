package com.example.gym_app.workout;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final SetLogService setLogService;

    @PostMapping("/generate")
    public WorkoutResponse generate() {
        return workoutService.generate();
    }

    @PutMapping("/{workoutId}/slots/{slotId}/sets")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logSets(
            @PathVariable Long workoutId,
            @PathVariable Long slotId,
            @Valid @RequestBody LogSetsRequest request
    ) {
        setLogService.logSets(workoutId, slotId, request);
    }
}