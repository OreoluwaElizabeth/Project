package com.semicolon.data.services;

import com.semicolon.data.DTO.request.*;
import com.semicolon.data.DTO.response.*;

public interface TodoService {
    TaskCreateResponse createTask(TaskCreateRequest taskCreateRequest);
    ViewTaskResponse viewTask(ViewTaskRequest viewTaskRequest);
    TaskCompleteResponse completeTask(TaskCompleteRequest taskCompleteRequest);
    UpdateResponse updateTaskDetails(TaskUpdateRequest taskUpdateRequest);
    TaskDeleteResponse deleteTask(TaskDeleteRequest taskDeleteRequest);
}
