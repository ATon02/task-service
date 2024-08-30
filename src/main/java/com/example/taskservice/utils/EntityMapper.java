package com.example.taskservice.utils;


import com.example.taskservice.dtos.request.DTOTaskEntityRequest;
import com.example.taskservice.dtos.response.DTOTaskEntityResponse;
import com.example.taskservice.models.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public TaskEntity dtoRequestToEntity(DTOTaskEntityRequest taskRequest) {
        TaskEntity task = new TaskEntity();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setUserId(taskRequest.getUserId());
        return  task;
    }

    public DTOTaskEntityResponse entityToDtoResponse(TaskEntity task) {
        DTOTaskEntityResponse dtoTaskEntityResponse = new DTOTaskEntityResponse();
        dtoTaskEntityResponse.setId(task.getId());
        dtoTaskEntityResponse.setTitle(task.getTitle());
        dtoTaskEntityResponse.setDescription(task.getDescription());
        dtoTaskEntityResponse.setStatus(task.getStatus());
        dtoTaskEntityResponse.setUserId(task.getUserId());
        return  dtoTaskEntityResponse;
    }

}
