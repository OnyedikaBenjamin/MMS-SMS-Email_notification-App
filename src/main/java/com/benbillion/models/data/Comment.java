package com.benbillion.models.data;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String body;
    private final LocalDateTime TIME_CREATED = LocalDateTime.now();
    @ManyToOne
    @JoinColumn
   private Todo todo;
}
