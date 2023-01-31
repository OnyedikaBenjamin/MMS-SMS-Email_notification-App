package com.benbillion.models;

import com.benbillion.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Todo {
    @Id private Long id;
    private String title;
    private String Body;
    private Status status;
    private LocalDateTime executionTime;
    @OneToMany
    private List<Comment> comments;




}
