package com.example.gym_app.workout;

import com.example.gym_app.user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetLogService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final SetLogRepository setLogRepository;
    private final CurrentUser currentUser;

    @Transactional
    public void logSets(Long workoutId, Long slotId, LogSetsRequest request) {
        Long userId = currentUser.id();

        WorkoutExercise slot = workoutExerciseRepository
                .findByIdAndWorkoutIdAndWorkoutUserId(slotId, workoutId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot not found"));

        if (slot.getExercise() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot log sets on a skipped slot");
        }


        setLogRepository.deleteByWorkoutExerciseId(slotId);


        List<SetLog> newLogs = new ArrayList<>();
        int setNumber = 1;
        for (SetEntry entry : request.sets()) {
            newLogs.add(new SetLog(
                    slot,
                    setNumber++,
                    entry.weightKg(),
                    entry.reps()
            ));
        }


        setLogRepository.saveAll(newLogs);
    }
}