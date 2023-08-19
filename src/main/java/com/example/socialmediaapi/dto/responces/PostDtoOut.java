package com.example.socialmediaapi.dto.responces;

import lombok.Data;

@Data
public class PostDtoOut {

    private String publisherUsername;
    private String title;
    private String text;
    private byte[] image;

}
