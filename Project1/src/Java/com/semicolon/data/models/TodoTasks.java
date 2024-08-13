package com.semicolon.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TodoTasks {
    private String id;
    private String title;
    private String description;
    private boolean isCompleted;
    private List<TodoTasks> todoTasks = new ArrayList<>();
    private String createdBy;
    private LocalDate dueDate;
    private String priority;
    private String category;
    private Boolean isRecurring;
}
