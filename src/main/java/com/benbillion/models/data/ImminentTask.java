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
@Setter
@Getter
@Table(name = "imminent")
public class ImminentTask {

    @Id
    private Long id;
    private String title;
    private Status status = Status.NotExecuted;
    private final LocalDateTime TIME_CREATED = LocalDateTime.now();
    private Date timeOfExecution;
    private String sendMeReminderMail;

    public ImminentTask(Long id, String title, Date timeOfExecution, String sendMeReminderMail) {
        this.title = title;
        this.timeOfExecution = timeOfExecution;
        this.sendMeReminderMail = sendMeReminderMail;
        this.id=id;
    }
}

