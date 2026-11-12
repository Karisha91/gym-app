package com.example.gym_app.workout;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

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
    @Test
    void setSchemeReturnsMaxReps() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        assertEquals(12, scheme.maxReps());
    }
    @Test
    void nextWeightIncreasesWhenSchemeHitsMaxReps() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        double result = ProgressionRules.nextWeight(scheme, List.of(12, 12, 12), 20.0);
        assertEquals(22.5, result);
    }

    @Test
    void nextWeightStaysTheSameWhenSchemeMissesMaxReps() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        double result = ProgressionRules.nextWeight(scheme, List.of(12, 12, 10), 20.0);
        assertEquals(20.0, result);
    }

    @Test
    void suggestWeightReturnsEmptyWhenNoHistory() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        assertEquals(Optional.empty(), ProgressionRules.suggestWeight(scheme, List.of()));
    }

    @Test
    void suggestWeightIncreasesWhenAllRepsHit() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        List<LoggedSet> history = List.of(
                new LoggedSet(20.0, 12),
                new LoggedSet(20.0, 12),
                new LoggedSet(20.0, 12)
        );
        assertEquals(Optional.of(22.5), ProgressionRules.suggestWeight(scheme, history));
    }

    @Test
    void suggestWeightStaysSameWhenOneRepMisses() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        List<LoggedSet> history = List.of(
                new LoggedSet(20.0, 12),
                new LoggedSet(20.0, 12),
                new LoggedSet(20.0, 10)
        );
        assertEquals(Optional.of(20.0), ProgressionRules.suggestWeight(scheme, history));
    }

    @Test
    void suggestWeightStaysSameWhenFewerSetsLogged() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        List<LoggedSet> history = List.of(
                new LoggedSet(20.0, 12),
                new LoggedSet(20.0, 12)
        );
        assertEquals(Optional.of(20.0), ProgressionRules.suggestWeight(scheme, history));
    }

    @Test
    void suggestWeightUsesHeaviestWeightFromMixedSets() {
        SetScheme scheme = new SetScheme(3, 8, 12);
        List<LoggedSet> history = List.of(
                new LoggedSet(20.0, 12),
                new LoggedSet(22.5, 12),
                new LoggedSet(20.0, 12)
        );

        assertEquals(Optional.of(25.0), ProgressionRules.suggestWeight(scheme, history));
    }


}