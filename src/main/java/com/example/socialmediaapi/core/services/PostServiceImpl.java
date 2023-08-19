package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.PostService;
import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.converters.PostOutConverter;
import com.example.socialmediaapi.core.entities.Post;
import com.example.socialmediaapi.dto.requests.PostDtoIn;
import com.example.socialmediaapi.dto.responces.PostDtoOut;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.converters.mappers.EntityDtoMapper;
import com.example.socialmediaapi.core.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageServiceImpl imageService;
    private final UserService userService;
    private final PostOutConverter postOutConverter;

    @Override
    @Transactional
    public void createOrUpdatePost(MultipartFile file, PostDtoIn postDtoIn, String username) {
        Post post = new Post();
        if (postDtoIn.getId() != null) {
            post = findPostById(postDtoIn.getId());
        }
        post.setTitle(postDtoIn.getTitle());
        post.setText(postDtoIn.getText());
        try {
            if (file != null) post.setImage(imageService.compressBytes(file.getBytes()));
        } catch (IOException e) {
            log.error("error adding image");
        }
        post.setUser(userService.findUserByUsername(username));
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDtoIn> findAllPosts(UUID userId) {
        userService.checkUserExist(userId);
        return postRepository.findAllByUserId(userId)
                .stream().map(EntityDtoMapper.INSTANCE::toPostDtoIn).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDtoOut> findSubscriberPosts(Integer page, Integer quantity, Boolean newFirst, String username) {
        Pageable pageable = Boolean.TRUE.equals((newFirst)) ? PageRequest.of(page - 1, quantity, Sort.by("updatedAt").descending()) : PageRequest.of(page - 1, quantity, Sort.by("updatedAt"));
        return postRepository.findSubscriberPosts(pageable, userService.findUserByUsername(username).getId())
                .map(postOutConverter::entityToDto);
    }

    @Override
    @Transactional
    public void deletePost(UUID postId, String username) {
        Post post = findPostById(postId);
        if (post.getUser().getUsername().equals(username)) {
            postRepository.deleteById(postId);
        } else throw new AccessDeniedException("Can't delete someone else's post");
    }

    @Override
    public Post findPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}
