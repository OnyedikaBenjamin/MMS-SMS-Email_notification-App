package com.benbillion.dtos;

import com.benbillion.enums.Status;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class CreateTodoRequest {

    private String title;
    private String Body;
    private Status todoStatus = Status.NotExecuted;
    private Date TIME_TO_BE_EXECUTED;
    private String dateAndTime = "dd/MM/yyyy HH:mm:ss";
    private final ZonedDateTime TIME_CREATED = ZonedDateTime.now();
}
