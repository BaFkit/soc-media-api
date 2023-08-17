package com.example.socialmediaapi.dto.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String login;
    private String password;

}
