package com.example.taskservice.repositories;


import com.example.taskservice.models.TaskEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface TaskEntityRepository extends ReactiveCrudRepository<TaskEntity, Long> {

    Flux<TaskEntity> findByUserId(Long userId);

}