package com.example.taskservice.services;

import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskEntityService {

    Mono<DTOTaskEntityResponse> getById(long id);

    Flux<DTOTaskEntityResponse> getAll();

    Mono<DTOTaskEntityResponse> save(DTOTaskEntityRequest taskEntityRequest);

    Mono<Void> deleteById(Long id);

    Mono<DTOTaskEntityResponse> update(Long id,DTOTaskEntityRequest taskEntity);

}
