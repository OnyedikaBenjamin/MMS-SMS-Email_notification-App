package com.benbillion.models.data;

import com.benbillion.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "todos")
public class Todo {
    @Id private Long id;
    private String title;
    private String body;
    private Status status = Status.NotExecuted;
    private final LocalDateTime TIME_CREATED = LocalDateTime.now();
    private Date timeOfExecution;
    @OneToMany
    private List<Comment> comments;
}