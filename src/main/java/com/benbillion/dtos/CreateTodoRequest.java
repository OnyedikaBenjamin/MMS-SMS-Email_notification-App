package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreateTodoRequest {
    @Id private Long id;
    private String title;
    private String body;
    private String stringSnippetOfDateAndTimeToBeExecuted = "2000-12-18 03:57:11";
}
