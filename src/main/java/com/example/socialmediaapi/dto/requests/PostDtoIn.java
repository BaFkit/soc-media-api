package com.example.socialmediaapi.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDtoIn {

    private UUID id;
    private String title;
    private String text;

}
