package com.example.socialmediaapi.dto.responces;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDtoOut {

    private UUID id;
    private String username;

}
