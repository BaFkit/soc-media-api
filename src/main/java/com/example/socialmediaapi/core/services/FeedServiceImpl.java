package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.FeedService;
import com.example.socialmediaapi.contracts.PostService;
import com.example.socialmediaapi.dto.responces.PostDtoOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {

    private final PostService postService;

    @Override
    public Page<PostDtoOut> getFeed(Integer page, Integer quantity, Boolean newFirst, String username) {
        if(page < 1) page = 1;
        return postService.findSubscriberPosts(page, quantity, newFirst, username);
    }

}
