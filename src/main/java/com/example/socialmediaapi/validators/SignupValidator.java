package com.example.socialmediaapi.validators;

import com.example.socialmediaapi.dto.SignupRequest;
import com.example.socialmediaapi.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SignupValidator {

    private final String EMAIL_REGEX = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private boolean emailValidator(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public void validate(SignupRequest signupRequest) {

        List<String> errors = new ArrayList<>();

        if (signupRequest.getUsername() == null || signupRequest.getUsername().isBlank()) errors.add("Username must not be empty");
        if (signupRequest.getPassword() == null || signupRequest.getPassword().isBlank()) errors.add("Password must not be empty");
        if (signupRequest.getEmail() == null || signupRequest.getEmail().isBlank()) errors.add("Email must not be empty");
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) errors.add("Password mismatch");
        if (!emailValidator(signupRequest.getEmail())) errors.add("The email address is invalid");
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

}
