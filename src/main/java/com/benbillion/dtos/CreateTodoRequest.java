package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreateTodoRequest {
    @Id private Long id;
    private String title;
    private String Body;
    private String StringSnippetOfDateAndTimeToBeExecuted;
}
