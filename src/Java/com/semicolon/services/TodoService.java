package com.semicolon.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;

public interface TodoService {
    TaskCreateResponse createTask(TaskCreateRequest taskCreateRequest);
    ViewTaskResponse viewTask(ViewTaskRequest viewTaskRequest);
    TaskCompleteResponse completeTask(TaskCompleteRequest taskCompleteRequest);
    TaskUpdateResponse updateTaskDetails(TaskUpdateRequest taskUpdateRequest);
    TaskDeleteResponse deleteTask(TaskDeleteRequest taskDeleteRequest);
}
