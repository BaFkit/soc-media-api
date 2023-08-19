package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.dto.requests.LoginRequest;
import com.example.socialmediaapi.dto.responces.LoginResponse;
import com.example.socialmediaapi.dto.requests.SignupRequest;
import com.example.socialmediaapi.exceptions.AuthenticationException;
import com.example.socialmediaapi.security.JwtTokenProvider;
import com.example.socialmediaapi.validators.LoginValidator;
import com.example.socialmediaapi.validators.SignupValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "Controller for authorization and registration")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginValidator loginValidator;
    private final SignupValidator signupValidator;

    @PostMapping
    @Operation(summary = "Authorization request",description = "Login can be made by login or email")
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
    @Operation(summary = "Registration request",description = "Sending user data for registration")
    public void registerUser(@RequestBody SignupRequest signupRequest) {
        signupValidator.validate(signupRequest);
        userService.createUser(signupRequest);
    }
}
