package com.example.socialmediaapi.validators;

import com.example.socialmediaapi.dto.requests.PostDtoIn;
import com.example.socialmediaapi.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostValidator {

    public void validate(PostDtoIn postDtoIn) {

        List<String> errors = new ArrayList<>();

        if (postDtoIn.getTitle() == null || postDtoIn.getTitle().isBlank()) errors.add("Title cannot be empty");
        if (!errors.isEmpty()) throw new ValidationException(errors);

    }

}
