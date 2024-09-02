package com.example.taskservice.controllers;

import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.models.enums.TaskStatus;
import com.example.taskservice.repositories.TaskEntityRepository;
import com.example.taskservice.services.TaskEntityService;
import com.example.taskservice.utils.EntityMapper;
import com.example.taskservice.utils.TaskValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest(TaskEntityController.class)
public class TaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TaskEntityService taskEntityService;

    @MockBean
    private TaskEntityRepository taskEntityRepository;

    @Mock
    private TaskValidator taskValidator;

    @Mock
    private EntityMapper entityMapper;


    @Test
    public void testCreateTask() {
        DTOTaskEntityRequest request = new DTOTaskEntityRequest("test title", "test description", TaskStatus.PENDING, 1L);
        DTOTaskEntityResponse response = new DTOTaskEntityResponse(1L, "test title", "test description", "PENDING", 1L);
        TaskEntity taskEntity = new TaskEntity("test title", "test description", "PENDING", 1L);
        when(taskEntityRepository.save(any(TaskEntity.class))).thenReturn(Mono.just(taskEntity));
        when(taskEntityService.save(any(DTOTaskEntityRequest.class))).thenReturn(Mono.just(response));
        webTestClient.post()
                .uri("/api/tasks")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DTOTaskEntityResponse.class)
                .value(savedTask -> {
                    assertEquals(1L, savedTask.getId());
                    assertEquals("test title", savedTask.getTitle());
                    assertEquals("test description", savedTask.getDescription());
                    assertEquals("PENDING", savedTask.getStatus());
                });
    }
}
