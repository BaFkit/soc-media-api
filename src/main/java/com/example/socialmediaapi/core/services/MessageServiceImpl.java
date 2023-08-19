package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.MessageService;
import com.example.socialmediaapi.contracts.SubscriptionService;
import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.core.entities.User;
import com.example.socialmediaapi.core.repositories.MessageRepository;
import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import com.example.socialmediaapi.core.entities.Message;
import com.example.socialmediaapi.converters.mappers.EntityDtoMapper;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.validators.MessageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    public final MessageRepository messageRepository;
    public final UserService userService;
    public final MessageValidator messageValidator;
    public final SubscriptionService subscriptionService;

    @Override
    @Transactional
    public void createMessage(MessageDtoIn messageDtoIn, String senderUsername) {
        messageValidator.validate(messageDtoIn);
        User sender = userService.findUserByUsername(senderUsername);
        User addressee = userService.findUserById(messageDtoIn.getAddresseeId());
        if (!subscriptionService.checkFriendship(sender.getId(), addressee.getId())) throw new AccessDeniedException("Messages can only be sent to friends");
        Message message = new Message();
        message.setSender(sender);
        message.setAddressee(addressee);
        message.setText(messageDtoIn.getText());
        messageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDtoOut> getAllIncomingMessages(String addressee) {
        return messageRepository.findAllByAddresseeId(userService.findUserByUsername(addressee).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toMessageDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDtoOut> getPersonalizedIncomingMessages(UUID sender, String addressee) {
        return messageRepository.findAllByAddresseeIdAndSenderId(userService.findUserByUsername(addressee).getId(), sender)
                .stream().map(EntityDtoMapper.INSTANCE::toMessageDto).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDtoOut> getAllSentMessages(String username) {
        return messageRepository.findAllBySenderId(userService.findUserByUsername(username).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toMessageDto).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDtoOut> getPersonalizedSentMessages(UUID addressee, String sender) {
        return messageRepository.findAllByAddresseeIdAndSenderId(addressee, userService.findUserByUsername(sender).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toMessageDto).collect(Collectors.toList());
    }
}
