package com.example.socialmediaapi.validators;

import com.example.socialmediaapi.dto.requests.MessageDtoIn;
import com.example.socialmediaapi.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageValidator {

    public void validate(MessageDtoIn messageDtoIn) {

        List<String> errors = new ArrayList<>();

        if (messageDtoIn.getAddresseeId() == null) errors.add("Missing recipient");
        if (messageDtoIn.getText() == null || messageDtoIn.getText().isBlank()) errors.add("Message must not be empty");
        if (!errors.isEmpty()) throw new ValidationException(errors);

    }
}
