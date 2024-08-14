package com.semicolon.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.data.models.TodoTasks;
import com.semicolon.data.repositories.TodoRepository;
import com.semicolon.enums.Category;
import com.semicolon.enums.Priority;
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
        Priority priority = Priority.valueOf(taskCreateRequest.getPriority());
        Category category = Category.valueOf(taskCreateRequest.getCategory());
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

        ViewTaskResponse response = new ViewTaskResponse();

        if (tasks.isEmpty()) {
            response.setMessage("Task not found");
            return response;
        }

        if(tasks.size() > 1) {
            throw new RuntimeException("Multiple tasks found with the same title");
        }

        TodoTasks task = tasks.get(0);
        response.setMessage("Task found successfully");
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setPriority(task.getPriority().name());
        response.setCategory(task.getCategory().name());
        response.setIsRecurring(task.getIsRecurring());

        return response;
    }

    @Override
    public TaskCompleteResponse completeTask(TaskCompleteRequest taskCompleteRequest) {
        List<TodoTasks> tasks = todoRepository.findByTitle(taskCompleteRequest.getTitle());
        TaskCompleteResponse response = new TaskCompleteResponse();
        if (tasks.isEmpty()) {
            response.setMessage("Task not found");
        } else if(!taskCompleteRequest.getIsCompleted()) {
            response.setMessage("Task not completed");
        } else {
            for(TodoTasks task: tasks) {
                task.setCompleted(true);
                todoRepository.save(task);
            }
            response.setMessage("Task completed successfully");
        }
        return response;
    }

    @Override
    public TaskUpdateResponse updateTaskDetails(TaskUpdateRequest taskUpdateRequest) {
        List<TodoTasks> tasks = todoRepository.findByTitle(taskUpdateRequest.getTitle());
        if (tasks.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        TodoTasks task = tasks.get(0);
        task.setTitle(taskUpdateRequest.getTitle());
        task.setDescription(taskUpdateRequest.getDescription());

        TodoTasks updatedTask = todoRepository.save(task);

        TaskUpdateResponse updateResponse = new TaskUpdateResponse();
        updateResponse.setMessage("Task updated successfully");
        return updateResponse;
    }


    @Override
    public TaskDeleteResponse deleteTask(TaskDeleteRequest taskDeleteRequest) {
        List<TodoTasks> tasks = todoRepository.findByTitle(taskDeleteRequest.getTaskName());
        if (tasks.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        todoRepository.delete(tasks.get(0));
        return new TaskDeleteResponse("Task deleted successfully");
    }
}
