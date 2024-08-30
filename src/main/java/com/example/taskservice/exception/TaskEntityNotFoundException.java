package com.example.taskservice.exception;

public class TaskEntityNotFoundException extends RuntimeException {
    public TaskEntityNotFoundException(String message) {
        super(message);
    }
}