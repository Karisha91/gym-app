package com.example.gym_app.workout;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionRulesTest {

    @Test
    void returnsTrueWhenAllSetsHitTarget() {
        assertTrue(ProgressionRules.shouldIncreaseWeight(List.of(12, 12, 12), 3, 12));
    }

    @Test
    void returnsFalseWhenOneSetMissesTarget() {
        assertFalse(ProgressionRules.shouldIncreaseWeight(List.of(12, 12, 10), 3, 12));
    }

    @Test
    void returnsFalseWhenFewerSetsAreLoggedThanPlanned() {
        assertFalse(ProgressionRules.shouldIncreaseWeight(List.of(12, 12), 3, 12));
    }

    @Test
    void returnsFalseWhenNoSetsAreLogged() {
        assertFalse(ProgressionRules.shouldIncreaseWeight(List.of(), 3, 12));
    }

    @Test
    void returnsTrueWhenSetsExceedMaxReps() {
        assertTrue(ProgressionRules.shouldIncreaseWeight(List.of(13, 13, 13), 3, 12));
    }

    @Test
    void returnsFalseWhenMoreSetsAreLoggedThanPlanned() {
        assertFalse(ProgressionRules.shouldIncreaseWeight(List.of(12, 12, 12, 12), 3, 12));
    }

    @Test
    void nextWeightIncreasesBy25WhenShouldIncreaseIsTrue() {
        double result = ProgressionRules.nextWeight(20.0, true);
        assertEquals(22.5, result);
    }

    @Test
    void nextWeightStaysTheSameWhenShouldIncreaseIsFalse() {
        double result = ProgressionRules.nextWeight(20.0, false);
        assertEquals(20.0, result);
    }
}