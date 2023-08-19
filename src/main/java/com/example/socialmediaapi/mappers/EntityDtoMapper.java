package com.example.socialmediaapi.mappers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.dto.responces.MessageDtoOut;
import com.example.socialmediaapi.entities.Message;
import com.example.socialmediaapi.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityDtoMapper {

    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    PostDto toDto(Post post);

    @Mapping(source = "sender.username", target = "senderUsername")
    @Mapping(source = "addressee.username", target = "addresseeUsername")
    MessageDtoOut toDto(Message message);
}
