package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreateTodoRequest {
    @Id private Long id;
    private String title;
    private String body;
    private String stringSnippetOfDateAndTimeToBeExecuted;
    private String reminderMail;
    private String sendMeReminderMail ;
}
