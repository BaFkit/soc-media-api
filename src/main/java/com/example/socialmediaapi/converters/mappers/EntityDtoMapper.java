package com.example.socialmediaapi.converters.mappers;

import com.example.socialmediaapi.dto.requests.PostDtoIn;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import com.example.socialmediaapi.core.entities.Message;
import com.example.socialmediaapi.core.entities.Post;
import com.example.socialmediaapi.core.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityDtoMapper {

    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    PostDtoIn toPostDtoIn(Post post);

    UserDtoOut toUserDtoOut(User user);

    @Mapping(source = "sender.username", target = "senderUsername")
    @Mapping(source = "addressee.username", target = "addresseeUsername")
    MessageDtoOut toMessageDto(Message message);
}
