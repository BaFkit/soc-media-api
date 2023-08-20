package com.example.socialmediaapi.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignupRequest {

    @Schema(title = "Username", description = " ", example = "Bob", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(title = "Email", description = "User email", example = "Bob@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(title = "Password", description = "User password", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(title = "Confirm password", description = "Re-entry password", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmPassword;

}
