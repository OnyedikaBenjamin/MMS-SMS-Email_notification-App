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
public class NotificationMailTimerService {
    private final EmailSenderService emailSenderService;
    private final TodoRepository todoRepository;
    private final ImminentTodoRepo imminentReminderRepo;
    private final OneHourDueRepo oneHourReminderRepo;
    private final TwentyFourHoursDueRepo twentyFourHoursReminderRepo;

    public NotificationMailTimerService(TodoRepository todoRepository, ImminentTodoRepo imminentReminder,
                                        OneHourDueRepo oneHourReminder, TwentyFourHoursDueRepo twentyFourHoursReminder,
                                        EmailSenderService emailSenderService) {
        this.todoRepository = todoRepository;
        this.imminentReminderRepo = imminentReminder;
        this.oneHourReminderRepo = oneHourReminder;
        this.twentyFourHoursReminderRepo = twentyFourHoursReminder;
        this.emailSenderService = emailSenderService;
    }
    private static final long TEN_SECONDS = 10000, TWENTY_SECONDS = 20000,
            ONE_HOUR = 3600000, TWENTY_FOUR_HOURS = 86400000;

    private boolean notAddedBefore(Todo todo) {
        return twentyFourHoursReminderRepo.findById(todo.getId()).isEmpty()
                && oneHourReminderRepo.findById(todo.getId()).isEmpty()
                && imminentReminderRepo.findById(todo.getId()).isEmpty();
    }
    private long differenceBetweenTimeOfExecutionAndNow(Todo todo){
        Date date = new Date();
        return todo.getTimeOfExecution().toInstant().toEpochMilli()
                - date.toInstant().toEpochMilli();
    }

    private boolean itsLessThanOneHourDue(Todo todo){
        return differenceBetweenTimeOfExecutionAndNow(todo) < ONE_HOUR
                && differenceBetweenTimeOfExecutionAndNow(todo) > 20;
    }

    @Scheduled(fixedDelay = TWENTY_SECONDS)
    public void addToDueTasks() {
        todoRepository.findAll().stream()
                .filter(todo -> todo.getSendMeReminderMail().equalsIgnoreCase("yes")
                        && differenceBetweenTimeOfExecutionAndNow(todo) < TWENTY_FOUR_HOURS
                        && differenceBetweenTimeOfExecutionAndNow(todo) > 0
                        && notAddedBefore(todo))
                .forEach(todo -> {
                    if (differenceBetweenTimeOfExecutionAndNow(todo) > ONE_HOUR) {
                        TwentyFourHoursDueTask twentyFourHoursDueTask = new TwentyFourHoursDueTask(todo.getId(),
                                todo.getTitle(), todo.getTimeOfExecution(), todo.getSendMeReminderMail());
                        twentyFourHoursReminderRepo.save(twentyFourHoursDueTask);}
                    else if(itsLessThanOneHourDue(todo)){
                        OneHourDueTask oneHourDueTask = new OneHourDueTask(todo.getId(),
                                todo.getTitle(), todo.getTimeOfExecution(), todo.getSendMeReminderMail());
                        oneHourReminderRepo.save(oneHourDueTask);}
                    else{
                        ImminentTask imminentTask = new ImminentTask(todo.getId(),
                                todo.getTitle(), todo.getTimeOfExecution(), todo.getSendMeReminderMail());
                        imminentReminderRepo.save(imminentTask);
                    }
                });
    }

    @Scheduled(fixedDelay = TEN_SECONDS)
    public void remindTwentyFourHoursDueTask(){
        twentyFourHoursReminderRepo.findAll().forEach(task -> {
            String messageBody = "You have less than 24 hours to execute task with name " + task.getTitle()
                    + ". The task time is " + task.getTimeOfExecution();
            emailSenderService.sendReminderMail(messageBody);
            OneHourDueTask oneHourDueTask = new OneHourDueTask(task.getId(), task.getTitle(),
                   task.getTimeOfExecution(), task.getSendMeReminderMail());
            oneHourReminderRepo.save(oneHourDueTask);
            twentyFourHoursReminderRepo.delete(task);
        });
    }

    @Scheduled(fixedDelay = TEN_SECONDS)
    public void remindOneHourDueTask(){
        Date date = new Date();
        oneHourReminderRepo.findAll().stream()
                .filter(task -> task.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() < ONE_HOUR
                        && task.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() > 20)
                .forEach(task -> {
                        String messageBody = "You have less than 1 hour to execute task with name " + task.getTitle()
                                + ". The task time is " + task.getTimeOfExecution();
                        emailSenderService.sendReminderMail(messageBody);
                        ImminentTask imminentTask = new ImminentTask(task.getId(), task.getTitle(),
                                task.getTimeOfExecution(), task.getSendMeReminderMail());
                        imminentReminderRepo.save(imminentTask);
                        oneHourReminderRepo.delete(task);
                    }
                );
    }

    @Scheduled(fixedDelay = TEN_SECONDS)
    public void remindImminentTask(){
        Date date = new Date();
        imminentReminderRepo.findAll().stream()
                .filter(task -> task.getTimeOfExecution().toInstant().toEpochMilli()
                        - date.toInstant().toEpochMilli() < 20000)
                .forEach(todo -> {
                    String messageBody = "Its time to execute task with name " + todo.getTitle();
                    emailSenderService.sendReminderMail(messageBody);
                    imminentReminderRepo.delete(todo);
                });
    }

}