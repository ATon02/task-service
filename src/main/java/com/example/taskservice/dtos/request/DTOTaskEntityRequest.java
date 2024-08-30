package com.example.taskservice.dtos.request;

import com.example.taskservice.models.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class DTOTaskEntityRequest {

    @Schema(example = "Title Test")
    private String title;
    @Schema(example = "Description Tests")
    private String description;
    @Schema(example = "PENDING")
    private TaskStatus status;
    @Schema(example = "0")
    private Long userId;

    public DTOTaskEntityRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
