package com.example.gym_app.user;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser {


    public static final String DEV_EMAIL = "dev@gym.local";

    private final UserRepository userRepository;

    public CurrentUser(UserRepository userRepository) { this.userRepository = userRepository; }

    public Long id() {
        return userRepository.findByEmail(DEV_EMAIL)
                .orElseThrow(() -> new IllegalStateException("Dev user missing - run with the 'dev' profile"))
                .getId();
    }
}