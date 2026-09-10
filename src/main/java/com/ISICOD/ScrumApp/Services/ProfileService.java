package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Profile.ProfileDTO;

public interface ProfileService {

    ProfileDTO getProfile(Integer utilisateurId);

}