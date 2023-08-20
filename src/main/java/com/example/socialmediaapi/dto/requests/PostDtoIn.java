package com.example.socialmediaapi.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class PostDtoIn {

    @Schema(title = "Id", description = "Post ID(filled in when post is updated)", example = "5", requiredMode = Schema.RequiredMode.AUTO)
    private UUID id;
    @Schema(title = "Title", description = "Post title", example = "Just title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(title = "Post content", description = "Post title", example = "Just the content in this post ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String text;

}
