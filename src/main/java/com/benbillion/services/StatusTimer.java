package com.benbillion.services;

import com.benbillion.enums.Status;
import com.benbillion.models.repository.TodoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;

public class StatusTimer {
    private final TodoRepository todoRepository;

    public StatusTimer(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Scheduled(cron = "0/3 * * * * *")
    public void changeTodoStatus() {
        todoRepository.findAll().stream()
                .filter(todo -> todo.getTimeOfExecution().toInstant().toEpochMilli()
                        < new Date().toInstant().toEpochMilli())
                .forEach(todoUndergoingExecution -> {
                    todoUndergoingExecution.setStatus(Status.UnderExecution);
                    todoRepository.save(todoUndergoingExecution);
                });
    }
}
