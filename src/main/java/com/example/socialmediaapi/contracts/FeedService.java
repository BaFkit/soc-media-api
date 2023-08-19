package com.example.socialmediaapi.contracts;

import com.example.socialmediaapi.dto.responces.PostDtoOut;
import org.springframework.data.domain.Page;

public interface FeedService {

    Page<PostDtoOut> getFeed(Integer page, Integer quantity, Boolean newFirst, String username);

}
