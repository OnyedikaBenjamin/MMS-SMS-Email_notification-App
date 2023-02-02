package com.benbillion.models.data;

import com.benbillion.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class Todo {
    @Id private Long id;
    private String title;
    private String Body;
    private Status status = Status.NotExecuted;
    private final ZonedDateTime TIME_CREATED = ZonedDateTime.now();
    private LocalDateTime TIME_TO_BE_EXECUTED;
    @OneToMany
    private List<Comment> comments;




}
