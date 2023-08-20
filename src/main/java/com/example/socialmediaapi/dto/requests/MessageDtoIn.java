package com.example.socialmediaapi.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageDtoIn {

    @Schema(title = "Id", description = "Message recipient Id", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID addresseeId;
    @Schema(title = "Message content", description = "Any text", example = "Just Message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String text;

}
