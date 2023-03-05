package com.benbillion.services;

import com.benbillion.dtos.*;
import com.benbillion.models.data.Comment;
import com.benbillion.models.data.Todo;
import com.benbillion.models.repository.CommentRepository;
import com.benbillion.models.repository.TodoRepository;
import exceptions.GenericHandlerException;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;
    private final EmailSenderService emailSenderService;

    public TodoServiceImpl(TodoRepository todoRepository,
                           CommentRepository commentRepository,
                           EmailSenderService emailSenderService
    ) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public CreateTodoResponse addTodo(CreateTodoRequest createTodoRequest) {
        Todo todo = new Todo();
        todo.setId(createTodoRequest.getId());
        todo.setBody(createTodoRequest.getBody());
        todo.setTitle(createTodoRequest.getTitle());
        todo.setSendMeReminderMail(createTodoRequest.getSendMeReminderMail());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            todo.setTimeOfExecution(formatter.parse(createTodoRequest.getStringSnippetOfDateAndTimeToBeExecuted()));
        } catch (IllegalArgumentException | ParseException e) {
            throw new RuntimeException(
                    "Invalid date and time" +
                            "Please use this format '2023-02-29 03:57:11' ");
        }
        todoRepository.save(todo);

        if (createTodoRequest.getSendMeReminderMail().equals("yes".toUpperCase())) {
            try {
                emailSenderService.sendCreatedTodoMail();
            } catch (GenericHandlerException | ArrayIndexOutOfBoundsException message) {
                throw new GenericHandlerException("There is no reminder Mail found");
            }

        }

        CreateTodoResponse response = new CreateTodoResponse();
        response.setMessage("Todo Successfully Created");
        return response;
    }

    @Override
    public UpdateTodoResponse editTask(UpdateTodoRequest updateTodoRequest,
                                       Long id) {
        Todo toBeUpdated = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with id " + id + " does not exist"));
        toBeUpdated.setTitle(updateTodoRequest.getTitle());
        toBeUpdated.setBody(updateTodoRequest.getBody());
        toBeUpdated.setSendMeReminderMail(updateTodoRequest.getSendMeReminderMail());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            toBeUpdated.setTimeOfExecution(formatter.parse(updateTodoRequest.getStringSnippetOfDateAndTimeToBeExecuted()));
        } catch (IllegalArgumentException | ParseException e) {
            throw new RuntimeException(
                    "Invalid date and time" +
                            "Please use this format '2000-12-18 03:57:11' ");
        }
        todoRepository.save(toBeUpdated);
        UpdateTodoResponse response = new UpdateTodoResponse();
        response.setMessage("Todo Successfully Updated");
        return response;
    }

    @Override
    public List<Todo> viewAllTodo() {
        return todoRepository.findAll();
    }

    @Override
    public String deleteAllTodo() {
        commentRepository.deleteAll();
        todoRepository.deleteAll();
        return "All todos have been cleared successfully";
    }

    @Override
    public List<Comment> showTodoComments(Long todoId) {
        return commentRepository.findByTodoId(todoId);
    }

    @Override
    public DeleteTodoResponse deleteTodo(Long id) {
        Todo queriedTodo = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with queried Id does not exist"));
        commentRepository.deleteAll(commentRepository.findByTodoId(id));
        todoRepository.delete(queriedTodo);

        DeleteTodoResponse response = new DeleteTodoResponse();
        response.setMessage("Todo deleted Successfully");
        return response;
    }

    @Override
    public Todo viewTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with queried id does not exist"));
    }

}