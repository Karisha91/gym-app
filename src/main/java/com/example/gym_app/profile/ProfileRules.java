package com.example.gym_app.profile;

import java.util.Set;

public final class ProfileRules {
    private ProfileRules() {}
    // Mirrors chk_profiles_minutes in V3. Change both together.
    public static final Set<Integer> SESSION_MINUTES = Set.of(30, 45, 60, 75, 90);
}