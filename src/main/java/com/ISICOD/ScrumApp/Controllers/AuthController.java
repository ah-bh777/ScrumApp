package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Auth.LoginRequestDTO;
import com.ISICOD.ScrumApp.DTOs.Auth.LoginResponseDTO;
import com.ISICOD.ScrumApp.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}