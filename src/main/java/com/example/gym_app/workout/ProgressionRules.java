package com.example.gym_app.workout;

import java.util.List;

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
}
