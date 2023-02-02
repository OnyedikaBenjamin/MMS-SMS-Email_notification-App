package com.benbillion.dtos;

import com.benbillion.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTodoRequest {
    private String title;
    private String Body;
    private LocalDateTime TIME_TO_BE_EXECUTED;
    private Status todoStatus = Status.NotExecuted;
}
