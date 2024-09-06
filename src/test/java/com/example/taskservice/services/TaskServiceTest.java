package com.example.taskservice.services;

import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.models.enums.TaskStatus;
import com.example.taskservice.repositories.TaskEntityRepository;
import com.example.taskservice.services.impl.TaskEntityServiceImpl;
import com.example.taskservice.utils.EntityMapper;
import com.example.taskservice.utils.TaskValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskEntityRepository repository;

    @InjectMocks
    private TaskEntityServiceImpl service;

    @Mock
    private TaskValidator taskValidator;

    @Mock
    private EntityMapper entityMapper;

    @Test
    public void shouldCreateTask() {
        TaskEntity task = new TaskEntity("test title", "test description", "PENDING", 1L);
        DTOTaskEntityRequest taskEntityRequest = new DTOTaskEntityRequest("test title", "test description", TaskStatus.PENDING, 1L);
        DTOTaskEntityResponse taskEntityResponse = new DTOTaskEntityResponse(1L, "test title", "test description", "PENDING", 1L);
        Mockito.when(taskValidator.validateDto(taskEntityRequest)).thenReturn(Mono.empty());
        Mockito.when(entityMapper.dtoRequestToEntity(taskEntityRequest)).thenReturn(task);
        Mockito.when(repository.save(any(TaskEntity.class))).thenReturn(Mono.just(task));
        Mockito.when(entityMapper.entityToDtoResponse(task)).thenReturn(taskEntityResponse);

        StepVerifier.create(service.save(taskEntityRequest))
                .expectNext(taskEntityResponse)
                .expectComplete()
                .verify();
    }
}
