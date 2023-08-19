package com.example.socialmediaapi.core.controllers;

import com.example.socialmediaapi.contracts.FeedService;
import com.example.socialmediaapi.dto.responces.PostDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
@Tag(name = "Feed", description = "Controller to get activity feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    @Operation(summary = "Feed request",description = "Getting a feed with friends' posts")
    public Page<PostDtoOut> getFeed(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "quantity", defaultValue = "10") Integer quantity,
                                    @RequestParam(name = "newFirst", defaultValue = "true") Boolean newFirst,
                                    Principal principal) {
        return feedService.getFeed(page, quantity, newFirst, principal.getName());
    }

}
