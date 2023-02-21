package com.benbillion.models.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String body;
    private final LocalDateTime TIME_CREATED = LocalDateTime.now();
    @ManyToOne
    @JoinColumn
   private Todo todo;

    public Comment(String body, Todo todo) {
        this.body = body;
        this.todo = todo;
    }
}
