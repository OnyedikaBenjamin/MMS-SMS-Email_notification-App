package com.benbillion.models.data;

import com.benbillion.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class FinishedTodo {
    @Id private Long id;
    private String title;
    private String Body;
    private Status status = Status.Finished;
    private LocalDateTime timeCreated;
    private LocalDateTime timeExecuted = LocalDateTime.now();
    @OneToMany
    private List<Comment> comments;
}
