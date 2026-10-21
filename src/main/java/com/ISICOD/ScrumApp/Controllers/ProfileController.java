package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Profile.ProfileDTO;
import com.ISICOD.ScrumApp.Services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileDTO> getProfile(
            @PathVariable Integer userId
    ) {

        ProfileDTO profile =
                profileService.getProfile(userId);

        return ResponseEntity.ok(profile);
    }
}