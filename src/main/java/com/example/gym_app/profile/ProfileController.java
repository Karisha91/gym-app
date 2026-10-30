package com.example.gym_app.profile;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) { this.profileService = profileService; }

    @GetMapping
    public ProfileResponse get() { return profileService.get(); }

    @PutMapping
    public ProfileResponse save(@Valid @RequestBody ProfileRequest request) {
        return profileService.save(request);
    }
}