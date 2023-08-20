package com.example.socialmediaapi.dto.responces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostDtoOut {

    @Schema(title = "Publisher", description = "Post username", example = "Jack")
    private String publisherUsername;
    @Schema(title = "Title", description = "Post title", example = "Just title")
    private String title;
    @Schema(title = "Text", description = "Post content", example = "Just text in this post")
    private String text;
    @Schema(title = "Image", description = "Post image, if any")
    private byte[] image;

}
