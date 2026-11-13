package com.example.gym_app.workout;

import com.example.gym_app.exercise.Equipment;

import java.util.List;
import java.util.Optional;

public class ProgressionRules {

    private static final double WEIGHT_INCREMENT = 2.5;


    public static boolean shouldIncreaseWeight(List<Integer> repsPerSet, int plannedSets, int maxReps) {
        if (repsPerSet.size() != plannedSets) {
            return false;
        }
        return repsPerSet.stream().allMatch(reps -> reps >= maxReps);
    }

    public static double nextWeight(double currentWeight, boolean shouldIncrease) {
        if (shouldIncrease) {
            return currentWeight + WEIGHT_INCREMENT;
        }
        return currentWeight;
    }

    public static double nextWeight(SetScheme scheme, List<Integer> repsPerSet, double currentWeight) {

        boolean increase = shouldIncreaseWeight(repsPerSet, scheme.sets(), scheme.maxReps());

        return nextWeight(currentWeight, increase);
    }

    public static Optional<Double> suggestWeight(SetScheme scheme, List<LoggedSet> lastSession) {
        if (lastSession == null || lastSession.isEmpty()) {
            return Optional.empty();
        }

        double maxWeight = lastSession.stream()
                .mapToDouble(LoggedSet::weightKg)
                .max()
                .getAsDouble();

        List<Integer> repsPerSet = lastSession.stream()
                .map(LoggedSet::reps)
                .toList();


        double nextWeight = nextWeight(scheme, repsPerSet, maxWeight);

        return Optional.of(nextWeight);
    }

    public static boolean supportsWeightProgression(Equipment equipment) {
        return equipment != Equipment.BODYWEIGHT;
    }
}
