package com.example.socialmediaapi.dto.responces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDtoOut {

    @Schema(title = "Text", description = "Content message", example = "any text in message")
    private String text;
    @Schema(title = "Sender", description = "The username of the person who sent the message", example = "Jack")
    private String senderUsername;
    @Schema(title = "Recipient", description = "The username of the recipient of the message", example = "Bob")
    private String addresseeUsername;
    @Schema(title = "Created at", description = "Date the message was created")
    private LocalDateTime createdAt;

}
