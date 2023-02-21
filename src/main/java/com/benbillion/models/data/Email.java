package com.benbillion.models.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reminderEmail;

    public Email(String reminderEmail) {
        this.reminderEmail = reminderEmail;
    }
}
