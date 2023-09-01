package com.example.recruitment_task.error;

import lombok.Getter;

@Getter
public class MessageErrorResponse {
    private int status;
    private String message;

    public MessageErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
