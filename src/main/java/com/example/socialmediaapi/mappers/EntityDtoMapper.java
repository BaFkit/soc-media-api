package com.example.socialmediaapi.mappers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.entities.Post;
import org.mapstruct.factory.Mappers;

public interface EntityDtoMapper {

    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    PostDto toDto(Post post);

}
