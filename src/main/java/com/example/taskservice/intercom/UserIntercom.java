package com.example.taskservice.intercom;

import com.example.taskservice.intercom.models.DTOUserEntityResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class UserIntercom {

    @Value("${header.internal}")
    private String internal;

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/users").build();

    public Mono<DTOUserEntityResponse> verifyUserExist(Long userId) {
        return webClient.get()
                .uri("/" + userId).header("internal", internal)
                .retrieve()
                .bodyToMono(DTOUserEntityResponse.class)
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException.NotFound) {
                        return Mono.error(new RuntimeException("User with id: " + userId + " not exist"));
                    }
                    return Mono.error(e);
                });
    }
}
