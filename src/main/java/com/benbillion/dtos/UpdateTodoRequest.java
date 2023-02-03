package com.benbillion.dtos;

import com.benbillion.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UpdateTodoRequest {
    private String title;
    private String Body;
    private Date TIME_TO_BE_EXECUTED;
    private Status todoStatus = Status.NotExecuted;
}
