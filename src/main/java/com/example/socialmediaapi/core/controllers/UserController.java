package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDtoOut> getAllUser() {
       return userService.findAllUser();
    }

}
