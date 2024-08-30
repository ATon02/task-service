package com.example.taskservice.utils;

import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.exception.InvalidTaskException;
import com.example.taskservice.models.TaskEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;


@Component
public class TaskValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DTOTaskEntityRequest task = (DTOTaskEntityRequest) target;

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            errors.rejectValue("title", "task.title.blank", "The 'title' field is empty");
            throw new InvalidTaskException("The 'title' field is empty");
        }

        if (task.getDescription() == null || task.getDescription().isBlank()) {
            errors.rejectValue("description", "task.description.blank", "The 'description' field is empty");
            throw new InvalidTaskException("The 'description' field is empty");
        }

        if (task.getStatus() == null) {
            errors.rejectValue("status", "task.status.blank", "The 'status' field is empty");
            throw new InvalidTaskException("The 'status' field is empty");
        }

        if (task.getUserId() == null || task.getUserId()<=0) {
            errors.rejectValue("userId", "task.userId.blank", "The 'userId' field is empty");
            throw new InvalidTaskException("The 'userId' field is empty or 0");
        }

    }

    public Mono<Void> validateDto(Object target) {
        try {
            Errors errors = new BeanPropertyBindingResult(target, "task");
            validate(target, errors);
            if (errors.hasErrors()) {
                return Mono.error(new InvalidTaskException("Validation errors occurred"));
            }
            return Mono.empty();
        } catch (InvalidTaskException e) {
            return Mono.error(e);
        }
    }
}
