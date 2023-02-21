package com.benbillion.models.data;

import com.benbillion.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class FinishedTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String Body;
    private Status status = Status.Finished;
    private LocalDateTime timeCreated;
    private LocalDateTime timeExecuted = LocalDateTime.now();
    @OneToMany
    private List<Comment> comments;

    public FinishedTodo(String title, String body, Status status, LocalDateTime timeCreated, LocalDateTime timeExecuted, List<Comment> comments) {
        this.title = title;
        Body = body;
        this.status = status;
        this.timeCreated = timeCreated;
        this.timeExecuted = timeExecuted;
        this.comments = comments;
    }
}
