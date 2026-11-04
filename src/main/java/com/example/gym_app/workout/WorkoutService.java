package com.example.gym_app.workout;

import com.example.gym_app.exercise.Exercise;
import com.example.gym_app.exercise.ExerciseRepository;
import com.example.gym_app.profile.Profile;
import com.example.gym_app.profile.ProfileRepository;
import com.example.gym_app.user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final ProfileRepository profileRepository;
    private final ExerciseRepository exerciseRepository;
    private final CurrentUser currentUser;

    @Transactional(readOnly = true)
    public List<PlannedSlot> generate() {

        Profile profile = profileRepository.findByUserId(currentUser.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No profile yet"));

        List<Exercise> exercises = WorkoutRules.filterExercisesByEquipment(
                exerciseRepository.findAll(),
                profile.getUsableEquipment()
        );

        return WorkoutRules.planWorkout(
                profile.getSessionMinutes(),
                exercises,
                profile.getGoal()
        );
    }
}