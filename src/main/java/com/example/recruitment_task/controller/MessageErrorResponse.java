package com.example.recruitment_task.controller;

import lombok.Getter;

@Getter
public class MessageErrorResponse {
    private String status;
    private String message;

    public MessageErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
