package com.semicolon.data.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String category;
    private Boolean isRecurring;
}
