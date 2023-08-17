package com.example.socialmediaapi.dto.responces;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDtoOut {

    private String text;
    private String sender;
    private String addressee;
    private LocalDateTime dateTime;

}
