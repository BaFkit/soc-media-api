package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.PostService;
import com.example.socialmediaapi.dto.requests.PostDtoIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts", description = "Post controller")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "Request to create a post",description = "Create a post with the current user and upload an image to the post")
    public void createPost(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody PostDtoIn postDtoIn, Principal principal) {
        postService.createOrUpdatePost(file, postDtoIn, principal.getName());
    }

    @GetMapping
    @Operation(summary = "Request posts",description = "Request to get all posts in a list")
    public List<PostDtoIn> getAllPosts(@RequestParam(name = "userId") UUID userId) {
        return postService.findAllPosts(userId);
    }

    @DeleteMapping
    @Operation(summary = "Delete post",description = "Delete a specific post")
    public void deletePost(@RequestParam(name = "postId") UUID postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
    }
}
