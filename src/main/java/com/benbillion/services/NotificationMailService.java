package com.benbillion.services;

import com.benbillion.models.data.ImminentTask;
import com.benbillion.models.data.OneHourDueTask;
import com.benbillion.models.data.Todo;
import com.benbillion.models.data.TwentyFourHoursDueTask;
import com.benbillion.models.repository.ImminentTodoRepo;
import com.benbillion.models.repository.OneHourDueRepo;
import com.benbillion.models.repository.TodoRepository;
import com.benbillion.models.repository.TwentyFourHoursDueRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableScheduling
@Configuration
@Component
public class NotificationMailService {
    private final EmailSenderService emailSenderService;
    private final TodoRepository todoRepository;
    private final ImminentTodoRepo imminentReminderRepo;
    private final OneHourDueRepo oneHourReminderRepo;
    private final TwentyFourHoursDueRepo twentyFourHoursReminderRepo;

    public NotificationMailService(TodoRepository todoRepository, ImminentTodoRepo imminentReminder,
                                   OneHourDueRepo oneHourReminder, TwentyFourHoursDueRepo twentyFourHoursReminder,
                                   EmailSenderService emailSenderService) {
        this.todoRepository = todoRepository;
        this.imminentReminderRepo = imminentReminder;
        this.oneHourReminderRepo = oneHourReminder;
        this.twentyFourHoursReminderRepo = twentyFourHoursReminder;
        this.emailSenderService = emailSenderService;
    }

    private boolean notAddedBefore(Todo todo) {
        return twentyFourHoursReminderRepo.findById(todo.getId()).isEmpty()
                && oneHourReminderRepo.findById(todo.getId()).isEmpty()
                && imminentReminderRepo.findById(todo.getId()).isEmpty();
    }

    @Scheduled(fixedDelay = 30000)
    public void addToTwentyFourHoursDueTask() {
        Date date = new Date();
        todoRepository.findAll().stream()
                .filter(todo -> todo.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() < 86400000
                        && todo.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() >0)
                .forEach(todo -> {
                    if (notAddedBefore(todo)) {
                        TwentyFourHoursDueTask twentyFourHoursDueTask = new TwentyFourHoursDueTask(todo.getId(),
                                todo.getTitle(), todo.getBody(), todo.getStatus(), todo.getTimeOfExecution(),
                                todo.getComments(), todo.getSendMeReminderMail());
                        twentyFourHoursReminderRepo.save(twentyFourHoursDueTask);
                    }
                });
    }

    @Scheduled(fixedDelay = 10000)
    public void remindTwentyFourHoursDueTask(){
        twentyFourHoursReminderRepo.findAll().forEach(task -> {
            String messageBody = "You have less than 24 hours to execute task with name " + task.getTitle()
                    + ". The task time is " + task.getTimeOfExecution();
            emailSenderService.sendReminderMail(messageBody);
            OneHourDueTask oneHourDueTask = new OneHourDueTask(task.getId(), task.getTitle(), task.getBody(),
                    task.getStatus(), task.getTimeOfExecution(), task.getComments(), task.getSendMeReminderMail());
            oneHourReminderRepo.save(oneHourDueTask);
            twentyFourHoursReminderRepo.delete(task);
        });
    }

    @Scheduled(fixedDelay = 10000)
    public void remindOneHourDueTask(){
        Date date = new Date();
        oneHourReminderRepo.findAll().stream()
                .filter(todo -> todo.getTimeOfExecution().toInstant().toEpochMilli()
                - date.toInstant().toEpochMilli() < 3600000)
                .forEach(task -> {
                        String messageBody = "You have less than 1 hour to execute task with name " + task.getTitle()
                        + ". The task time is " + task.getTimeOfExecution();
                        emailSenderService.sendReminderMail(messageBody);
                    ImminentTask imminentTask = new ImminentTask(task.getId(), task.getTitle(), task.getBody(),
                            task.getStatus(), task.getTimeOfExecution(), task.getComments(), task.getSendMeReminderMail());
                        imminentReminderRepo.save(imminentTask);
                        oneHourReminderRepo.delete(task);
                });
    }

    @Scheduled(fixedDelay = 10000)
    public void remindImminentTask(){
        Date date = new Date();
        imminentReminderRepo.findAll().stream()
                .filter(task -> task.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() < 10000)
                .forEach(todo -> {
                    String messageBody = "Its time to execute task with name " + todo.getTitle();
                    emailSenderService.sendReminderMail(messageBody);
                    imminentReminderRepo.delete(todo);
                });
    }

}