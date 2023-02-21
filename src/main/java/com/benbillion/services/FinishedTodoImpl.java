package com.benbillion.services;

import com.benbillion.models.data.FinishedTodo;
import com.benbillion.models.data.Todo;
import com.benbillion.models.repository.FinishedTodoRepo;
import com.benbillion.models.repository.TodoRepository;
import exceptions.GenericHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedTodoImpl implements FinishedTodoService {
    @Autowired
    FinishedTodoRepo finishedTodoRepo;
    @Autowired TodoService todoService;
    @Autowired TodoRepository todoRepository;

    @Override
    public String markAsDone(Long id) {
        Todo query = todoRepository.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Todo with queried id " + id + " cannot be found"));
        com.benbillion.models.data.FinishedTodo finishedTodo = new com.benbillion.models.data.FinishedTodo();
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
    public String deleteAllFinishedTodo() {
        finishedTodoRepo.deleteAll();
        return "Successful";
    }

    @Override
    public List<FinishedTodo> viewAllFinishedTodo() {
        return finishedTodoRepo.findAll();
    }

    @Override
    public FinishedTodo findFinishedTodoById(Long id) {
        return finishedTodoRepo.findById(id)
                .orElseThrow(() -> new GenericHandlerException("Finished Todo with id " + id + " does not exist"));
    }

    @Override
    public String deleteFinishedTodo(Long id) {
            FinishedTodo queried = finishedTodoRepo.findById(id)
                    .orElseThrow(()-> new GenericHandlerException("Finished Todo with queried Id does not exist"));
            finishedTodoRepo.delete(queried);
        return "Deleted Successfully";
    }


}
