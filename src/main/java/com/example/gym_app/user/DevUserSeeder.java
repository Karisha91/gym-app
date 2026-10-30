package com.example.gym_app.user;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevUserSeeder implements ApplicationRunner {


    private final UserRepository userRepository;

    public DevUserSeeder(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsByEmail(CurrentUser.DEV_EMAIL)) {
            userRepository.save(new User(CurrentUser.DEV_EMAIL));
        }
    }
}