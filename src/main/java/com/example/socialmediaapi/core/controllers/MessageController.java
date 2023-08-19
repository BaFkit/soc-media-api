package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.MessageService;
import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
@Tag(name = "Message", description = "Controller for creating and receiving messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Create message",description = "Create messages for user friend")
    public void createMessage(@RequestBody MessageDtoIn message, Principal principal) {
        messageService.createMessage(message, principal.getName());
    }

    @GetMapping("/incoming")
    @Operation(summary = "Incoming messages",description = "Get all incoming messages for the current user")
    public List<MessageDtoOut> getAllIncomingMessages(Principal principal) {
        return messageService.getAllIncomingMessages(principal.getName());
    }

    @GetMapping("/incoming/{id}")
    @Operation(summary = "Personalized incoming message",description = "Get all messages from a specific user")
    public List<MessageDtoOut> getPersonalizedIncomingMessages(@PathVariable UUID id, Principal principal) {
        return messageService.getPersonalizedIncomingMessages(id, principal.getName());
    }

    @GetMapping("/sent")
    @Operation(summary = "Request all sent messages",description = "Query all sent messages for the current user")
    public List<MessageDtoOut> getAllSentMessages(Principal principal) {
        return messageService.getAllSentMessages(principal.getName());
    }

    @GetMapping("/sent/{id}")
    @Operation(summary = "Request sent messages",description = "Request sent messages for a specific user")
    public List<MessageDtoOut> getPersonalizedSentMessages(@PathVariable UUID id, Principal principal) {
        return messageService.getPersonalizedSentMessages(id, principal.getName());
    }
}
