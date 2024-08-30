package com.example.taskservice.intercom;

import com.example.taskservice.intercom.models.DTOUserEntityResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class UserIntercom {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/api/users").build();

    public Mono<DTOUserEntityResponse> verifyUserExist(Long userId) {
        return webClient.get()
                .uri("/" + userId)
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
