package com.example.socialmediaapi.dto.responces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDtoOut {

    @Schema(title = "Id", description = "User id", example = "5")
    private UUID id;
    @Schema(title = "Username", description = "Username", example = "Bob")
    private String username;

}
