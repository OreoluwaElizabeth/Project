package com.semicolon.data.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.data.models.TodoTasks;
import com.semicolon.data.repositories.TodoRepository;
import com.semicolon.enums.Category;
import com.semicolon.enums.Priority;
import com.semicolon.services.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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
        taskCreateRequest.setPriority("MEDIUM");
        taskCreateRequest.setCategory("WORK");
        taskCreateRequest.setIsRecurring(true);

        TaskCreateResponse taskCreateResponse = todoService.createTask(taskCreateRequest);
        assertNotNull(taskCreateResponse);
        assertEquals("Task created successfully", taskCreateResponse.getMessage());
    }

    @Test
    public void testTaskFound() {
        TodoTasks tasks = new TodoTasks();
        tasks.setTitle("Wash");
        tasks.setDescription("Wash plates");
        tasks.setDueDate(LocalDate.now());
        tasks.setPriority(Priority.MEDIUM);
        tasks.setCategory(Category.WORK);
        tasks.setIsRecurring(true);
        todoRepository.save(tasks);

        ViewTaskRequest request = new ViewTaskRequest();
        request.setTitle("Wash");

        ViewTaskResponse response = todoService.viewTask(request);

        assertNotNull(response);
        assertEquals("Task found successfully", response.getMessage());
        assertEquals("Wash", response.getTitle());
        assertEquals("Wash plates", response.getDescription());
        assertEquals(LocalDate.now(), response.getDueDate());
        assertEquals(Priority.MEDIUM, Priority.valueOf(response.getPriority()));
        assertEquals(Category.WORK, Category.valueOf(response.getCategory()));
        assertEquals(true, response.getIsRecurring());
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
        task.setTitle("Holiday");
        task.setDescription("My baby is leaving me tomorrow");
        todoRepository.save(task);
        System.out.println("Task saved with ID: " + task.getId());

        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setTitle("Holiday");
        taskUpdateRequest.setDescription("My baby loves songs");

        TaskUpdateResponse response = todoService.updateTaskDetails(taskUpdateRequest);

        assertNotNull(response);
        assertEquals("Task updated successfully", response.getMessage());

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
