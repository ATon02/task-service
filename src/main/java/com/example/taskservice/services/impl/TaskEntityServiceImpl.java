package com.example.taskservice.services.impl;

import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import com.example.taskservice.exception.InvalidTaskException;
import com.example.taskservice.exception.TaskEntityNotCreatedException;
import com.example.taskservice.exception.TaskEntityNotFoundException;
import com.example.taskservice.exception.TaskEntityNotUpdatedException;
import com.example.taskservice.intercom.UserIntercom;
import com.example.taskservice.repositories.TaskEntityRepository;
import com.example.taskservice.services.TaskEntityService;
import com.example.taskservice.utils.EntityMapper;
import com.example.taskservice.utils.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskEntityServiceImpl implements TaskEntityService {

    @Autowired
    private TaskEntityRepository taskEntityRepository;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private TaskValidator taskValidator;
    @Autowired
    private UserIntercom userIntercom;

    @Override
    public Mono<DTOTaskEntityResponse> getById(long id) {
        return taskEntityRepository.findById(id)
                .map(entityMapper::entityToDtoResponse)
                .switchIfEmpty(Mono.error(new TaskEntityNotFoundException("Task with ID " + id + " not found")));
    }

    @Override
    public Flux<DTOTaskEntityResponse> getAll() {
        return taskEntityRepository.findAll()
                .map(entityMapper::entityToDtoResponse)
                .switchIfEmpty(Flux.error(new TaskEntityNotFoundException("Not found tasks")));
    }

    @Override
    public Mono<DTOTaskEntityResponse> save(DTOTaskEntityRequest taskEntityRequest) {
        return taskValidator.validateDto(taskEntityRequest)
          .then(userIntercom.verifyUserExist(taskEntityRequest.getUserId())
             .switchIfEmpty(Mono.error(new TaskEntityNotCreatedException("User with id: " + taskEntityRequest.getUserId() + " not exist")))
          )
          .flatMap(user -> taskEntityRepository.save(entityMapper.dtoRequestToEntity(taskEntityRequest)))
          .map(entityMapper::entityToDtoResponse)
          .switchIfEmpty(Mono.error(new TaskEntityNotCreatedException("Task not saved")))
          .onErrorMap(InvalidTaskException.class, ex -> new TaskEntityNotCreatedException(ex.getMessage()))
          .onErrorMap(RuntimeException.class, ex -> new TaskEntityNotCreatedException(ex.getMessage()));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return taskEntityRepository.findById(id)
          .switchIfEmpty(Mono.error(new TaskEntityNotFoundException("Task with id " + id + " not found")))
          .flatMap(taskEntity -> {taskEntityRepository.delete(taskEntity).subscribe();
              return Mono.empty();
          });
    }

    @Override
    public Mono<DTOTaskEntityResponse> update(Long id, DTOTaskEntityRequest taskEntity) {
        return taskValidator.validateDto(taskEntity)
           .then(userIntercom.verifyUserExist(taskEntity.getUserId())
              .switchIfEmpty(Mono.error(new TaskEntityNotUpdatedException("User with id: " + taskEntity.getUserId() + " not exist")))
           )
                .flatMap(user->taskEntityRepository.findById(id)
                        .flatMap(existTaskEntity -> {
                            existTaskEntity.setTitle(taskEntity.getTitle());
                            existTaskEntity.setDescription(taskEntity.getDescription());
                            existTaskEntity.setStatus(taskEntity.getStatus());
                            existTaskEntity.setUserId(taskEntity.getUserId());
                            return taskEntityRepository.save(existTaskEntity)
                                    .map(entityMapper::entityToDtoResponse)
                                    .switchIfEmpty(Mono.error(new TaskEntityNotCreatedException("Task not updated")));
                        }).switchIfEmpty(Mono.error(new TaskEntityNotFoundException("Task with ID " + id + " not found")))
                )
                .onErrorMap(InvalidTaskException.class, ex -> new TaskEntityNotCreatedException(ex.getMessage()))
                .onErrorMap(RuntimeException.class, ex -> new TaskEntityNotCreatedException(ex.getMessage()));
    }


}
