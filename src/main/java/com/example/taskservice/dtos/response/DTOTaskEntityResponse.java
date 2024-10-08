package com.example.taskservice.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class DTOTaskEntityResponse {

    @Schema(example = "0")
    private Long id;
    @Schema(example = "Title Test")
    private String title;
    @Schema(example = "Description Tests")
    private String description;
    @Schema(example = "PENDING")
    private String status;
    @Schema(example = "0")
    private Long userId;

    public DTOTaskEntityResponse() {
    }

    public DTOTaskEntityResponse(Long id, String title, String description, String status, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
