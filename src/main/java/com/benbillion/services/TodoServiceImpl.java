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
        todo.setBody(createTodoRequest.getBody());
        todo.setTitle(createTodoRequest.getTitle());
        try {
            todo.setTIME_TO_BE_EXECUTED(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(createTodoRequest.getDateAndTime()));
        }catch (GenericHandlerException | ParseException ignored){
            throw new RuntimeException(
                    "Invalid date and time format" +
                            "Please use this format dd/MM/yyyy HH:mm:ss");
        }
        todoRepository.save(todo);
        CreateTodoResponse response = new CreateTodoResponse();
        response.setMessage("Category Successfully Created");
        return response;
    }
    @Override
    public UpdateTodoResponse editTask(UpdateTodoRequest updateTodoRequest, Long id) {
        Todo toBeUpdated = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with id "+id+" does not exist"));
        toBeUpdated.setTitle(updateTodoRequest.getTitle());
        toBeUpdated.setBody(updateTodoRequest.getBody());
        toBeUpdated.setTIME_TO_BE_EXECUTED(updateTodoRequest.getTIME_TO_BE_EXECUTED());
        todoRepository.save(toBeUpdated);
        UpdateTodoResponse response = new UpdateTodoResponse();
        response.setMessage("Category Successfully Created");
        return response;
    }
    @Override
    public String markAsDone(Long id) {
        // firstly, add to the list of finished task and then delete.
        Todo query  = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with queried id "+id+" cannot be found"));
        FinishedTodo finishedTodo = new FinishedTodo();
        finishedTodo.setTitle(query.getTitle());
        finishedTodo.setBody(query.getBody());
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
