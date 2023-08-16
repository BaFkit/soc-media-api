package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.dto.LoginRequest;
import com.example.socialmediaapi.dto.LoginResponse;
import com.example.socialmediaapi.dto.SignupRequest;
import com.example.socialmediaapi.exceptions.AuthenticationException;
import com.example.socialmediaapi.security.JwtTokenProvider;
import com.example.socialmediaapi.services.UserService;
import com.example.socialmediaapi.validators.LoginValidator;
import com.example.socialmediaapi.validators.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginValidator loginValidator;
    private final SignupValidator signupValidator;

    @PostMapping
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        loginValidator.validate(loginRequest);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Incorrect login or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getLogin());
        String token = jwtTokenProvider.generateToken(userDetails);
        return new LoginResponse(token);

    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody SignupRequest signupRequest) {
        signupValidator.validate(signupRequest);
        userService.createUser(signupRequest);
    }
}
