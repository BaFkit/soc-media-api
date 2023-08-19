package com.example.socialmediaapi.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageDtoIn {

    private UUID addresseeId;
    private String text;

}
