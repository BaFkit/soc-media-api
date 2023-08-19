package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Controller for user management")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Getting users",description = "Getting a list of users")
    public List<UserDtoOut> getAllUser() {
       return userService.findAllUser();
    }

}
