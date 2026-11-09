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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final ProfileRepository profileRepository;
    private final ExerciseRepository exerciseRepository;
    private final CurrentUser currentUser;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    @Transactional
    public WorkoutResponse generate() {
        Profile profile = profileRepository.findByUserId(currentUser.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No profile yet"));

        List<Exercise> exercises = WorkoutRules.filterExercisesByEquipment(
                exerciseRepository.findAll(),
                profile.getUsableEquipment()
        );

        List<PlannedSlot> plannedSlots = WorkoutRules.planWorkout(
                profile.getSessionMinutes(),
                exercises,
                profile.getGoal()
        );

        return saveWorkout(profile, plannedSlots);
    }

    private WorkoutResponse saveWorkout(Profile profile, List<PlannedSlot> plannedSlots) {
        Workout workout = new Workout(
                profile.getUserId(),
                profile.getGoal(),
                profile.getSessionMinutes()
        );
        Workout savedWorkout = workoutRepository.save(workout);

        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        for (int i = 0; i < plannedSlots.size(); i++) {
            PlannedSlot slot = plannedSlots.get(i);
            workoutExercises.add(new WorkoutExercise(
                    savedWorkout,
                    i + 1,
                    slot.slotSpec().pattern(),
                    slot.exercise().orElse(null),
                    slot.scheme().sets(),
                    slot.scheme().minReps(),
                    slot.scheme().maxReps()
            ));
        }


        List<WorkoutExercise> savedRows = workoutExerciseRepository.saveAll(workoutExercises);

        return WorkoutResponse.from(savedWorkout, savedRows);
    }
}