package com.benbillion.models.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id private Long id;
    private String body;
    private LocalDateTime time;
    @ManyToOne
   private Todo todo;

}