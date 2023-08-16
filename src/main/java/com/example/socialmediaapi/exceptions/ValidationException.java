package com.example.socialmediaapi.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException{
    private List<String> errorFieldsMessages;

    public ValidationException(List<String> errorFieldsMessages) {
        super(String.join(", ", errorFieldsMessages));
        this.errorFieldsMessages = errorFieldsMessages;
    }

    public List<String> getErrorFieldsMessages() {
        return errorFieldsMessages;
    }

    public void setErrorFieldsMessages(List<String> errorFieldsMessages) {
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
