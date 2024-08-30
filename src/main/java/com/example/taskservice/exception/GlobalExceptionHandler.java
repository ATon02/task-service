package com.example.taskservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskEntityNotFoundException.class)
    public Mono<ResponseEntity<String>> handleTaskEntityNotFoundException(TaskEntityNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler(TaskEntityNotCreatedException.class)
    public Mono<ResponseEntity<String>> handleTaskEntityNotCreatedException(TaskEntityNotCreatedException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(TaskEntityNotUpdatedException.class)
    public Mono<ResponseEntity<String>> handleTaskEntityNotUpdatedException(TaskEntityNotUpdatedException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(NotValidFieldException.class)
    public Mono<ResponseEntity<String>> handleEmptyFieldException(NotValidFieldException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTaskException.class)
    public Mono<ResponseEntity<String>> handleTaskEntityNotFoundException(InvalidTaskException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }
}