package com.benbillion.services;

import com.benbillion.models.data.FinishedTodo;

import java.util.List;

public interface FinishedTodoService {
    List<FinishedTodo> viewAllFinishedTodo();
    FinishedTodo findFinishedTodoById(Long id);
    String deleteAllFinishedTodo();
    String markAsDone(Long id);
    String deleteFinishedTodo(Long id);
}
