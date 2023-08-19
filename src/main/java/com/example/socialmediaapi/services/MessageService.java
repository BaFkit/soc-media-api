package com.example.socialmediaapi.services;

import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import com.example.socialmediaapi.entities.Message;
import com.example.socialmediaapi.entities.User;
import com.example.socialmediaapi.mappers.EntityDtoMapper;
import com.example.socialmediaapi.repositories.MessageRepository;
import com.example.socialmediaapi.validators.MessageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    public final MessageRepository messageRepository;
    public final UserService userService;
    public final MessageValidator messageValidator;

    @Transactional
    public void createMessage(MessageDtoIn messageDtoIn, String senderUsername) {
        messageValidator.validate(messageDtoIn);
        User sender = userService.findUserByUsername(senderUsername);
        User addressee = userService.findUserById(messageDtoIn.getAddresseeId());
        Message message = new Message();
        message.setSender(sender);
        message.setAddressee(addressee);
        message.setText(messageDtoIn.getText());
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDtoOut> getAllIncomingMessages(String addressee) {
        return messageRepository.findAllByAddresseeId(userService.findUserByUsername(addressee).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDtoOut> getPersonalizedIncomingMessages(UUID sender, String addressee) {


        return messageRepository.findAllByAddresseeIdAndSenderId(userService.findUserByUsername(addressee).getId(), sender)
                .stream().map(EntityDtoMapper.INSTANCE::toDto).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<MessageDtoOut> getAllSentMessages(String username) {

        return messageRepository.findAllBySenderId(userService.findUserByUsername(username).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toDto).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<MessageDtoOut> getPersonalizedSentMessages(UUID addressee, String sender) {

        return messageRepository.findAllByAddresseeIdAndSenderId(addressee, userService.findUserByUsername(sender).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
