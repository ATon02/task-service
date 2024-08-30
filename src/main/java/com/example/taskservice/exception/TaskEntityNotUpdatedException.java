package com.example.taskservice.exception;

public class TaskEntityNotUpdatedException extends RuntimeException {
    public TaskEntityNotUpdatedException(String message) {
        super(message);
    }
}