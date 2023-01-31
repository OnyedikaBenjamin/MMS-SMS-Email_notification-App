package com.benbillion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comments {
    @Id private Long id;
    private String body;
    private LocalDateTime time;


}
