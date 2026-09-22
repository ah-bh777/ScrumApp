package com.ISICOD.ScrumApp.DTOs.Auth;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}