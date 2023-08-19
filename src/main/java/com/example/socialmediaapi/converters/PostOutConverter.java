package com.example.socialmediaapi.converters;

import com.example.socialmediaapi.contracts.ImageService;
import com.example.socialmediaapi.dto.responces.PostDtoOut;
import com.example.socialmediaapi.core.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostOutConverter {

    private final ImageService imageService;

    public PostDtoOut entityToDto(Post post) {
        PostDtoOut postDtoOut = new PostDtoOut();
        postDtoOut.setTitle(post.getTitle());
        postDtoOut.setText(post.getText());
        postDtoOut.setPublisherUsername(post.getUser().getUsername());
        if (post.getImage() != null) postDtoOut.setImage(imageService.decompressBytes(post.getImage()));
        return postDtoOut;
    }

}
