package com.example.socialmediaapi.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(title = "Login", description = "Username or email", example = "user1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;
    @Schema(title = "Password", description = "Password", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
