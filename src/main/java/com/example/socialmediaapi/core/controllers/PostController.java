package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.PostService;
import com.example.socialmediaapi.dto.requests.PostDtoIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody PostDtoIn postDtoIn, Principal principal) {
        postService.createOrUpdatePost(file, postDtoIn, principal.getName());
    }

    @GetMapping
    public List<PostDtoIn> getAllPosts(@RequestParam(name = "userId") UUID userId) {
        return postService.findAllPosts(userId);
    }

    @DeleteMapping
    public void deletePost(@RequestParam(name = "postId") UUID postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
    }
}
