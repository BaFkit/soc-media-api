package com.example.socialmediaapi.contracts;

import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    void createMessage(MessageDtoIn messageDtoIn, String senderUsername);
    List<MessageDtoOut> getAllIncomingMessages(String addressee);
    List<MessageDtoOut> getPersonalizedIncomingMessages(UUID sender, String addressee);
    List<MessageDtoOut> getAllSentMessages(String username);
    List<MessageDtoOut> getPersonalizedSentMessages(UUID addressee, String sender);

}
