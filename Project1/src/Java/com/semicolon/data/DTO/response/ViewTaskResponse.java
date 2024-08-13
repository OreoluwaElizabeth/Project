package com.semicolon.data.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewTaskResponse {
    private String message;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String category;
    private Boolean isRecurring;
}
