package com.benbillion.models.data;

import com.benbillion.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String body;
    private Status status = Status.NotExecuted;
    private final LocalDateTime TIME_CREATED = LocalDateTime.now();
    private Date timeOfExecution;
    @OneToMany
    private List<Comment> comments;
    private String sendMeReminderMail;

    public Todo(String title, String body, Status status, Date timeOfExecution, List<Comment> comments, String sendMeReminderMail) {
        this.title = title;
        this.body = body;
        this.status = status;
        this.timeOfExecution = timeOfExecution;
        this.comments = comments;
        this.sendMeReminderMail = sendMeReminderMail;
    }
}