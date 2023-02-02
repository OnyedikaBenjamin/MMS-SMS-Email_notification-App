package com.benbillion.services;

import com.benbillion.dtos.*;
import com.benbillion.models.data.FinishedTodo;
import com.benbillion.models.data.Todo;
import java.util.List;

public interface TodoService {
    CreateTodoResponse addTodo(CreateTodoRequest createTodoRequest);
    UpdateTodoResponse editTask(UpdateTodoRequest updateTodoRequest, Long Id);
    DeleteTodoResponse deleteTodo(Long Id);
    String deleteFinishedTodo(Long id);
    String markAsDone(Long id);
    List<Todo> viewAllTodo();
    List<FinishedTodo> viewAllFinishedTodo();
    FinishedTodo findFinishedTodoById(Long id);
}
