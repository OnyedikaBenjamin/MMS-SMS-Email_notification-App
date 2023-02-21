package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UpdateTodoRequest {
    @Id
    private Long id;
    private String title;
    private String Body;
    private String StringSnippetOfDateAndTimeToBeExecuted = "2000-12-18 03:57:11";
    private String sendMeReminderMail;
}
