package com.example.socialmediaapi.contracts;

import com.example.socialmediaapi.dto.requests.PostDtoIn;
import com.example.socialmediaapi.dto.responces.PostDtoOut;
import com.example.socialmediaapi.core.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostDtoIn> findAllPosts(UUID userId);
    Page<PostDtoOut> findSubscriberPosts(Integer page, Integer quantity, Boolean newFirst, String username);
    Post findPostById(UUID id);
    void createOrUpdatePost(MultipartFile file, PostDtoIn postDtoIn, String username);
    void deletePost(UUID postId, String username);


}
