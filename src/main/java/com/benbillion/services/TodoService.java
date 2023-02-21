package com.benbillion.services;

import com.benbillion.dtos.*;
import com.benbillion.models.data.Comment;
import com.benbillion.models.data.Todo;
import java.util.List;

public interface TodoService {
    CreateTodoResponse addTodo(CreateTodoRequest createTodoRequest);
    UpdateTodoResponse editTask(UpdateTodoRequest updateTodoRequest, Long Id);
    DeleteTodoResponse deleteTodo(Long Id);
    Todo viewTodo(Long id);
    List<Todo> viewAllTodo();
    String deleteAllTodo();
    List<Comment> showTodoComments(Long todoId);
}
