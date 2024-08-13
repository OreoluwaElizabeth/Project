package com.semicolon.data.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoTaskTest {

    @Test
    public void testTitle() {
        TodoTasks todoTasks = new TodoTasks();
        todoTasks.setTitle("Daily goals");
        assertEquals("Daily goals", todoTasks.getTitle());
    }

    @Test
    public void testDescription(){
        TodoTasks todoTasks = new TodoTasks();
        todoTasks.setDescription("Wash plates");
        assertEquals("Wash plates", todoTasks.getDescription());
    }

    @Test
    public void testIsCompleted() {
        TodoTasks todoTasks = new TodoTasks();
        todoTasks.setCompleted(true);
        assertTrue(todoTasks.isCompleted());
    }

    @Test
    public void testCompleted() {
        TodoTasks todoTasks = new TodoTasks();
        todoTasks.setCompleted(false);
        assertFalse(todoTasks.isCompleted());
    }

    @Test
    public void testGetTodo() {
        TodoTasks todoTasks = new TodoTasks();
        List<TodoTasks> tasks = new ArrayList<>();
        todoTasks.setTodoTasks(tasks);
        assertEquals(tasks, todoTasks.getTodoTasks());
    }

    @Test
    public void testSetTodo() {
        TodoTasks todoTasks = new TodoTasks();
        List<TodoTasks> tasks = new ArrayList<>();
        tasks.add(new TodoTasks());
        todoTasks.setTodoTasks(tasks);
        assertEquals(1, todoTasks.getTodoTasks().size());
    }
}
