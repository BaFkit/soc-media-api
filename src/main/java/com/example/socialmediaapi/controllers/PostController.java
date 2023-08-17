package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestParam(name = "file") MultipartFile file, @RequestBody PostDto postDto, Principal principal) {
        postService.createOrUpdatePost(file, postDto, principal.getName());
    }

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(name = "username") String username) {
        return postService.findAllPosts(username);
    }

    @DeleteMapping
    public void deletePost(@RequestParam(name = "postId") String postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
    }
}
