package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Auth.LoginRequestDTO;
import com.ISICOD.ScrumApp.DTOs.Auth.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);
}