package com.example.taskservice.exception;

public class TaskEntityNotCreatedException extends RuntimeException {
    public TaskEntityNotCreatedException(String message) {
        super(message);
    }
}