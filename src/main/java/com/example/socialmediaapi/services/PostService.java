package com.example.socialmediaapi.services;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.entities.Post;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.mappers.EntityDtoMapper;
import com.example.socialmediaapi.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final UserService userService;

    @Transactional
    public void createOrUpdatePost(MultipartFile file, PostDto postDto, String username) {
        Post post = new Post();
        if (postDto.getId() != null) {
            post = findPostById(postDto.getId());
        }
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        try {
            if(file != null) post.setImage(imageService.compressBytes(file.getBytes()));
        } catch (IOException e) {
            log.error("error adding image");
        }
        post.setUser(userService.findUserByUsername(username));
        postRepository.save(post);
    }

    public List<PostDto> findAllPosts(String username) {
        return postRepository.findAllByUserId(userService.findUserByUsername(username).getId())
                .stream().map(EntityDtoMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(String postId, String username) {
        Post post = findPostById(UUID.fromString(postId));
        if (post.getUser().getUsername().equals(username)) {
            postRepository.deleteById(UUID.fromString(postId));
        } else throw new AccessDeniedException("Can't delete someone else's post");
    }

    private Post findPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}
