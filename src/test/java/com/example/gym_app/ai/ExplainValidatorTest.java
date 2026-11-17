package com.example.gym_app.ai;

import com.example.gym_app.exercise.Equipment;
import com.example.gym_app.exercise.MovementPattern;
import com.example.gym_app.profile.Goal;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplainValidatorTest {

    @Test
    void validResponseForTwoSlotsIsAccepted() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "Goblet Squat is a good fit for your goal."),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit for your goal.")
        );
        assertTrue(ExplainValidator.isValid(request(), response));
    }

    @Test
    void responseWithDigitInRationaleIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "Bump to 25 kg on the Goblet Squat."),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void duplicatePositionIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "Goblet Squat is a good fit."),
                new ExplainResponse.ResponseSlot(1, "Dumbbell Bench Press is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void missingPositionIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "Goblet Squat is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void extraPositionIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "Goblet Squat is a good fit."),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit."),
                new ExplainResponse.ResponseSlot(3, "Something extra.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void blankRationaleIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, "   "),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void nullRationaleIsRejected() {
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, null),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void rationaleExactly150CharsIsAccepted() {
        String text = "a".repeat(150);   // valid: no digits, exactly 150
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, text),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit.")
        );
        assertTrue(ExplainValidator.isValid(request(), response));
    }

    @Test
    void rationale151CharsIsRejected() {
        String text = "a".repeat(151);
        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, text),
                new ExplainResponse.ResponseSlot(2, "Dumbbell Bench Press is a good fit.")
        );
        assertFalse(ExplainValidator.isValid(request(), response));
    }

    @Test
    void nullElementInResponseIsRejected() {
        // Construct a response with a null element in the slots list
        ExplainResponse response = new ExplainResponse(java.util.Arrays.asList(
                new ExplainResponse.ResponseSlot(1, "Goblet Squat is a good fit."),
                null
        ));
        assertFalse(ExplainValidator.isValid(request(), response));
    }



    private static ExplainRequest request() {
        return new ExplainRequest(
                Goal.MUSCLE,
                Set.of(Equipment.DUMBBELL),
                List.of(
                        new ExplainRequest.RequestSlot(1, "Goblet Squat", MovementPattern.SQUAT),
                        new ExplainRequest.RequestSlot(2, "Dumbbell Bench Press", MovementPattern.PUSH)
                )
        );
    }

    private static ExplainResponse response(ExplainResponse.ResponseSlot... slots) {
        return new ExplainResponse(List.of(slots));
    }
    @Test
    void fallbackTextIsValidByOurOwnRules() {

        ExplainResponse response = response(
                new ExplainResponse.ResponseSlot(1, ExplainValidator.FALLBACK_WHY),
                new ExplainResponse.ResponseSlot(2, ExplainValidator.FALLBACK_WHY)
        );

        assertTrue(ExplainValidator.isValid(request(), response));
    }

    @Test
    void fallbackHasOneSlotPerRequestSlot() {
        ExplainRequest request = request();
        ExplainResponse fallback = ExplainValidator.fallbackFor(request);

        assertNotNull(fallback);
        assertEquals(2, fallback.slots().size());
        assertEquals(1, fallback.slots().get(0).position());
        assertEquals(2, fallback.slots().get(1).position());
        assertEquals(ExplainValidator.FALLBACK_WHY, fallback.slots().get(0).why());
        assertEquals(ExplainValidator.FALLBACK_WHY, fallback.slots().get(1).why());
    }

    @Test
    void fallbackAlwaysPassesValidation() {
        ExplainRequest request = request();
        assertTrue(ExplainValidator.isValid(request, ExplainValidator.fallbackFor(request)));
    }
}