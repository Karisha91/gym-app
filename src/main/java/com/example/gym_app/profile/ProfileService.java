package com.example.gym_app.profile;

import com.example.gym_app.user.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final CurrentUser currentUser;

    public ProfileService(ProfileRepository profileRepository, CurrentUser currentUser) {
        this.profileRepository = profileRepository;
        this.currentUser = currentUser;
    }

    @Transactional()
    public ProfileResponse get() {
        Profile profile = profileRepository.findByUserId(currentUser.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No profile yet"));
        return ProfileResponse.from(profile);
    }

    @Transactional
    public ProfileResponse save(ProfileRequest request) {
        Long userId = currentUser.id();
        Profile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> new Profile(userId));
        profile.apply(request.goal(), request.daysPerWeek(),
                request.sessionMinutes(), request.equipment());
        return ProfileResponse.from(profileRepository.save(profile));
    }
}