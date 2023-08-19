package com.example.socialmediaapi.dto.responces;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDtoOut {

    private String text;
    private String senderUsername;
    private String addresseeUsername;
    private LocalDateTime createdAt;

}
