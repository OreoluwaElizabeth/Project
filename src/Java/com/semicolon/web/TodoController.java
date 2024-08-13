package com.semicolon.web;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/create")
    public TaskCreateResponse createTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        return todoService.createTask(taskCreateRequest);
    }

    @PostMapping("/view")
    public ViewTaskResponse viewTask(@RequestBody ViewTaskRequest viewTaskRequest) {
        return todoService.viewTask(viewTaskRequest);
    }

    @PostMapping("/complete")
    public TaskCompleteResponse completeTask(@RequestBody TaskCompleteRequest taskCompleteRequest) {
        return todoService.completeTask(taskCompleteRequest);
    }

    @PostMapping("/update")
    public TaskUpdateResponse updateTaskDetails(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        return todoService.updateTaskDetails(taskUpdateRequest);
    }

    @PostMapping("/delete")
    public TaskDeleteResponse deleteTask(@RequestBody TaskDeleteRequest taskDeleteRequest) {
        return todoService.deleteTask(taskDeleteRequest);
    }
}
