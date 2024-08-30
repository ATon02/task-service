package com.example.taskservice.controllers;


import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import com.example.taskservice.exception.TaskEntityNotFoundException;
import com.example.taskservice.services.TaskEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
public class TaskEntityController {
    @Autowired
    private TaskEntityService taskEntityService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Task By Id", description = "Return a Task id to get.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task data found."),
            @ApiResponse(responseCode = "400", description = "Bad request: Task with ID not found", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<DTOTaskEntityResponse> getTaskEntityById(@PathVariable Long id) {
        return taskEntityService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Tasks", description = "Return all Tasks of system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks data found."),
            @ApiResponse(responseCode = "400", description = "Bad request: Not found Tasks",content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Flux<DTOTaskEntityResponse> getAllTasks() {
        return taskEntityService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create Task", description = "Save Task in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task saved correctly"),
            @ApiResponse(responseCode = "400", description = "Bad request: Task Not Created or Bad request: The field is empty or not valid", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<DTOTaskEntityResponse> createTaskEntity(@RequestBody DTOTaskEntityRequest taskEntity) {
        return taskEntityService.save(taskEntity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Task By Id", description = "Update Task in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated correctly"),
            @ApiResponse(responseCode = "400", description = "Bad request: Task not found or Bad request: The field not is valid or not valid", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<DTOTaskEntityResponse> updateTaskEntity(@RequestBody DTOTaskEntityRequest taskEntity,
                                             @PathVariable Long id) {
        return taskEntityService.update(id,taskEntity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task By Id", description = "Delete Task in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task delete."),
            @ApiResponse(responseCode = "400", description = "Bad request: Task not found", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<ResponseEntity<Object>> deleteTaskEntity(@PathVariable Long id) {
        return taskEntityService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(TaskEntityNotFoundException.class, ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
}
