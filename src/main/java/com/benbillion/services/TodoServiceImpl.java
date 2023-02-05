package com.benbillion.services;

import com.benbillion.dtos.*;
import com.benbillion.models.data.FinishedTodo;
import com.benbillion.models.data.Todo;
import com.benbillion.models.repository.FinishedTodoRepo;
import com.benbillion.models.repository.TodoRepository;
import exceptions.GenericHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    TodoRepository todoRepository;
    FinishedTodoRepo finishedTodoRepo;
    TodoService todoService;

    @Override
    public CreateTodoResponse addTodo(CreateTodoRequest createTodoRequest) {
        Todo todo = new Todo();
        todo.setId(createTodoRequest.getId());
        todo.setBody(createTodoRequest.getBody());
        todo.setTitle(createTodoRequest.getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                todo.setTimeOfExecution(formatter.parse(createTodoRequest.getStringSnippetOfDateAndTimeToBeExecuted()));
            } catch (IllegalArgumentException | ParseException e) {
            throw new RuntimeException(
                    "Invalid date and time" +
                            "Please use this format '2000-12-18 03:57:11' ");
            }
        todoRepository.save(todo);
        CreateTodoResponse response = new CreateTodoResponse();
        response.setMessage("Todo Successfully Created");
        return response;
    }
    @Override
    public UpdateTodoResponse editTask(UpdateTodoRequest updateTodoRequest, Long id) {
        Todo toBeUpdated = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with id " + id + " does not exist"));
        toBeUpdated.setTitle(updateTodoRequest.getTitle());
        toBeUpdated.setBody(updateTodoRequest.getBody());
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
    public String markAsDone(Long id) {
        Todo query  = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with queried id "+id+" cannot be found"));
        FinishedTodo finishedTodo = new FinishedTodo();
        finishedTodo.setId(query.getId());
        finishedTodo.setTitle(query.getTitle());
        finishedTodo.setBody(query.getBody());
        finishedTodo.setTimeCreated(query.getTIME_CREATED());
        finishedTodo.setComments(query.getComments());
        finishedTodoRepo.save(finishedTodo);

        todoService.deleteTodo(query.getId());
        return "Successful";
    }
    @Override
    public List<Todo> viewAllTodo() {
        return todoRepository.findAll();
    }
    @Override
    public List<FinishedTodo> viewAllFinishedTodo() {
        return finishedTodoRepo.findAll();
    }
    @Override
    public FinishedTodo findFinishedTodoById(Long id) {
        return finishedTodoRepo.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Finished Todo with id "+id+" does not exist"));
    }
    @Override
    public String deleteAllTodo() {
        todoRepository.deleteAll();
        return "All todos have been cleared successfully";
    }
    @Override
    public String deleteAllFinishedTodo() {
        finishedTodoRepo.deleteAll();
        return "Successful";
    }
    @Override
    public DeleteTodoResponse deleteTodo(Long id) {
        todoRepository.deleteById(id);
        DeleteTodoResponse response = new DeleteTodoResponse();
        response.setMessage("Todo deleted Successfully");
        return response;
    }
    @Override
    public String deleteFinishedTodo(Long id) {
        finishedTodoRepo.deleteById(id);
        return "Deleted Successfully";
    }

}