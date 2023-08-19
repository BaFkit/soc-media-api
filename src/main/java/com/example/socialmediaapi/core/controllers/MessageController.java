package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.MessageService;
import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void createMessage(@RequestBody MessageDtoIn message, Principal principal) {
        messageService.createMessage(message, principal.getName());
    }

    @GetMapping("/incoming")
    public List<MessageDtoOut> getAllIncomingMessages(Principal principal) {
        return messageService.getAllIncomingMessages(principal.getName());
    }

    @GetMapping("/incoming/{id}")
    public List<MessageDtoOut> getPersonalizedIncomingMessages(@PathVariable UUID id, Principal principal) {
        return messageService.getPersonalizedIncomingMessages(id, principal.getName());
    }

    @GetMapping("/sent")
    public List<MessageDtoOut> getAllSentMessages(Principal principal) {
        return messageService.getAllSentMessages(principal.getName());
    }

    @GetMapping("/sent/{id}")
    public List<MessageDtoOut> getPersonalizedSentMessages(@PathVariable UUID id, Principal principal) {
        return messageService.getPersonalizedSentMessages(id, principal.getName());
    }
}
