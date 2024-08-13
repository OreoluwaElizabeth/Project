package com.semicolon.data.services;

import com.semicolon.data.DTO.request.*;
import com.semicolon.data.DTO.response.*;
import com.semicolon.data.models.TodoTasks;
import com.semicolon.data.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class TodoServiceImplTest {
    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testCreateTask() {
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setTitle("Eat");
        taskCreateRequest.setDescription("Eat when it's 8:00");
        taskCreateRequest.setDueDate(LocalDate.now());
        taskCreateRequest.setPriority("High");
        taskCreateRequest.setCategory("Food");
        taskCreateRequest.setIsRecurring(false);

        TaskCreateResponse taskCreateResponse = todoService.createTask(taskCreateRequest);
        assertThat(taskCreateResponse).isNotNull();
        assertThat(taskCreateResponse.getMessage()).isEqualTo("Task created successfully");
    }

    @Test
    public void testTaskFound() {
        TodoTasks tasks = new TodoTasks();
        tasks.setTitle("Wash");
        tasks.setDescription("Wash plates");
        tasks.setDueDate(LocalDate.now());
        tasks.setPriority("Medium");
        tasks.setCategory("Dirty stuffs");
        tasks.setIsRecurring(true);

        todoRepository.save(tasks);

        ViewTaskRequest request = new ViewTaskRequest();
        request.setTitle("Wash");

        ViewTaskResponse response = todoService.viewTask(request);
        assertNotNull(response);
        assertEquals("Task found successfully", response.getMessage());
        assertEquals(tasks.getTitle(), response.getTitle());
        assertEquals(tasks.getDescription(), response.getDescription());
        assertEquals(tasks.getDueDate(), response.getDueDate());
        assertEquals(tasks.getPriority(), response.getPriority());
        assertEquals(tasks.getCategory(), response.getCategory());
        assertEquals(tasks.getIsRecurring(), response.getIsRecurring());
    }

    @Test
    public void testTaskNotFound() {
        ViewTaskRequest request = new ViewTaskRequest();
        request.setTitle("Play");
        ViewTaskResponse response = todoService.viewTask(request);
        assertNotNull(response);
        assertEquals("Task not found", response.getMessage());
    }

    @Test
    public void testCompleteTask() {
        TodoTasks task = new TodoTasks();
        task.setTitle("Write code");
        task.setCompleted(false);
        todoRepository.save(task);
        TaskCompleteRequest request = new TaskCompleteRequest();
        request.setTitle("Write code");
        request.setIsCompleted(true);
        TaskCompleteResponse response = todoService.completeTask(request);
        assertEquals("Task completed successfully", response.getMessage());
    }

    @Test
    public void testUpdateDetails() {
        TodoTasks task = new TodoTasks();
        task.setId("12");
        task.setTitle("Washing plates");
        task.setDescription("Wash after writing code");
        todoRepository.save(task);

        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTaskId("12");
        request.setTitle("Rehearsals");
        request.setDescription("Go to church for rehearsals");
        UpdateResponse response = todoService.updateTaskDetails(request);

        assertNotNull(response);
        assertEquals("Task updated successfully", response.getMessage());
        assertEquals("12", response.getTaskId());

        TodoTasks updatedTask = todoRepository.findById("12").orElse(null);
        assertNotNull(updatedTask);
        assertEquals("Rehearsals", updatedTask.getTitle());
        assertEquals("Go to church for rehearsals", updatedTask.getDescription());
    }

    @Test
    public void testDeleteTask() {
        TodoTasks tasks = new TodoTasks();
        tasks.setTitle("Check");
        tasks.setDescription("Describe");
        todoRepository.save(tasks);

        TaskDeleteRequest request = new TaskDeleteRequest();
        request.setTaskId(tasks.getId());
        TaskDeleteResponse response = todoService.deleteTask(request);

        assertNotNull(response);
        assertEquals("Task deleted successfully", response.getMessage());
    }
}
