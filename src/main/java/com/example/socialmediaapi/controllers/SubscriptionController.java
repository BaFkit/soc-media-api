package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{id}")
    public void friendRequest(@PathVariable UUID id , Principal principal) {
        subscriptionService.subscribe(id, principal.getName());
    }

    @PostMapping("/{id}/accept")
    public void acceptRequest(@PathVariable UUID id, Principal principal) {
        subscriptionService.acceptFriendship(id, principal.getName());
    }


}
