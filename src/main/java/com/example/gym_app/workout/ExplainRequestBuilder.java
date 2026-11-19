package com.example.gym_app.workout;

import com.example.gym_app.ai.ExplainRequest;
import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.profile.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExplainRequestBuilder {

    public static ExplainRequest build(Goal goal, Set<Equipment> equipment, List<PlannedSlot> plannedSlots) {
        List<ExplainRequest.RequestSlot> requestSlots = new ArrayList<>();
        for (int i = 0; i < plannedSlots.size(); i++) {
            PlannedSlot slot = plannedSlots.get(i);
            if (slot.exercise().isPresent()) {
                requestSlots.add(new ExplainRequest.RequestSlot(
                        i + 1,
                        slot.exercise().get().getName(),
                        slot.slotSpec().pattern()
                ));
            }
        }
        return new ExplainRequest(goal, equipment, requestSlots);
    }
}