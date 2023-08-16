package com.example.socialmediaapi.validators;

import com.example.socialmediaapi.dto.LoginRequest;
import com.example.socialmediaapi.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginValidator {

    public void validate(LoginRequest loginRequest) {

        List<String> errors = new ArrayList<>();

        if (loginRequest.getLogin() == null || loginRequest.getLogin().isBlank()) errors.add("Username must not be empty");
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) errors.add("Password must not be empty");
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
