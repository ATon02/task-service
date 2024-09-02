package com.example.taskservice.respositories;

import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskEntityRepository taskRepository;

    @Test
    public void shouldSaveTask() {
        TaskEntity task = new TaskEntity("test title", "test description", "PENDING", 1L);
        taskRepository.save(task)
                .as(StepVerifier::create)
                .expectNextMatches(savedTask -> savedTask.getId() != null && "test title".equals(savedTask.getTitle()))
                .verifyComplete();
    }
}
