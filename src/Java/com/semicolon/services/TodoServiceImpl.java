package com.semicolon.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.data.models.TodoTasks;
import com.semicolon.data.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TaskCreateResponse createTask(TaskCreateRequest taskCreateRequest) {
        String title = taskCreateRequest.getTitle();
        String description = taskCreateRequest.getDescription();
        LocalDate dueDate = taskCreateRequest.getDueDate();
        String priority = taskCreateRequest.getPriority();
        String category = taskCreateRequest.getCategory();
        Boolean isRecurring = taskCreateRequest.getIsRecurring();

        TodoTasks todoTasks = new TodoTasks();
        todoTasks.setTitle(title);
        todoTasks.setDescription(description);
        todoTasks.setDueDate(dueDate);
        todoTasks.setPriority(priority);
        todoTasks.setCategory(category);
        todoTasks.setIsRecurring(isRecurring);

        todoRepository.save(todoTasks);
        return new TaskCreateResponse("Task created successfully");
    }

    @Override
    public ViewTaskResponse viewTask(ViewTaskRequest viewTaskRequest) {
        List<TodoTasks> tasks = todoRepository.findByTitle(viewTaskRequest.getTitle());

        if (tasks.isEmpty()) {
            ViewTaskResponse response = new ViewTaskResponse();
            response.setMessage("Task not found");
            return response;
        }
        if(tasks.size() > 1) {
            throw new RuntimeException("Multiple tasks found with the same title");
        }
        TodoTasks task = tasks.get(0);
        ViewTaskResponse response = new ViewTaskResponse();
        response.setMessage("Task found successfully");
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setPriority(task.getPriority());
        response.setCategory(task.getCategory());
        response.setIsRecurring(task.getIsRecurring());

        return response;
    }

    @Override
    public TaskCompleteResponse completeTask(TaskCompleteRequest taskCompleteRequest) {
        List<TodoTasks> tasks = todoRepository.findByTitle(taskCompleteRequest.getTitle());
        TaskCompleteResponse response = new TaskCompleteResponse();
        if (tasks.isEmpty()) {
            response.setMessage("Task not found");
        } else {
            for(TodoTasks task: tasks) {
                task.setCompleted(taskCompleteRequest.getIsCompleted());
                todoRepository.save(task);
            }
            response.setMessage("Task completed successfully");
        }
        return response;
    }

    @Override
    public TaskUpdateResponse updateTaskDetails(TaskUpdateRequest taskUpdateRequest) {
        TodoTasks tasks = todoRepository.findById(taskUpdateRequest.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));
        tasks.setTitle(taskUpdateRequest.getTitle());
        tasks.setDescription(taskUpdateRequest.getDescription());

        TodoTasks updatedTask = todoRepository.save(tasks);

        TaskUpdateResponse updateResponse = new TaskUpdateResponse();
        updateResponse.setMessage("Task updated successfully");
        return updateResponse;
    }


    @Override
    public TaskDeleteResponse deleteTask(TaskDeleteRequest taskDeleteRequest) {
        TodoTasks tasks = todoRepository.findById(taskDeleteRequest.getTaskId()).orElseThrow(()-> new RuntimeException("Task not found"));
        todoRepository.delete(tasks);
        return new TaskDeleteResponse("Task deleted successfully");
    }
}
