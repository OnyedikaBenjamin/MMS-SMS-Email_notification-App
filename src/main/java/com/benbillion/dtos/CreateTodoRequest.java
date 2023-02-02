package com.benbillion.dtos;

import com.benbillion.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class CreateTodoRequest {

    private String title;
    private String Body;
    private Status todoStatus = Status.NotExecuted;
    private ZonedDateTime TIME_TO_BE_EXECUTED;
    private final ZonedDateTime TIME_CREATED = ZonedDateTime.now();
}
