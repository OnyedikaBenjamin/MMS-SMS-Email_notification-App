package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateTodoRequest {
    @Id private Long id;
    private String title;
    private String Body;
    private String StringSnippetOfDateAndTimeToBeExecuted;
}
