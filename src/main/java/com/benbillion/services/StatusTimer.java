package com.benbillion.services;

import com.benbillion.enums.Status;
import com.benbillion.models.data.Todo;
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
        Date date = new Date();
        todoRepository.findAll().stream()
                .filter(todo -> todo.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() < 0
                && todo.getStatus().equals(Status.NotExecuted))
                .forEach(todo -> {
                    todo.setStatus(Status.Finished);
                    todoRepository.save(todo);
                });
    }

}
