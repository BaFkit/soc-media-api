package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Controller for managing user subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{id}")
    @Operation(summary = "Friendship request",description = "Send a friend request and become a user subscriber")
    public void friendRequest(@PathVariable UUID id , Principal principal) {
        subscriptionService.subscribe(id, principal.getName());
    }

    @PostMapping("/{id}/accept")
    @Operation(summary = "Accept friendship",description = "Friendship acceptance request in response to a submitted offer")
    public void acceptRequest(@PathVariable UUID id, Principal principal) {
        subscriptionService.acceptFriendship(id, principal.getName());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Unsubscribe",description = "Unfollow a user")
    public void unsubscribeRequest(@PathVariable UUID id, Principal principal) {
        subscriptionService.unsubscribe(id, principal.getName());
    }

    @DeleteMapping("/friends/{id}")
    @Operation(summary = "Unfriend",description = "Unfriend a user")
    public void unfriendRequest(@PathVariable UUID id, Principal principal) {
        subscriptionService.unfriend(id, principal.getName());
    }
}
