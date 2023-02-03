package com.benbillion.controller;

import com.benbillion.dtos.*;
import com.benbillion.models.data.FinishedTodo;
import com.benbillion.models.data.Todo;
import com.benbillion.services.TodoService;
import com.benbillion.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/todo")
public class Controller {
    @Autowired
    TodoService todoService;

    @PostMapping("/add")
    public ResponseEntity<?> addTodo(@RequestBody CreateTodoRequest createTodoRequest, HttpServletRequest httpServletRequest){
        CreateTodoResponse todo = todoService.addTodo(createTodoRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody UpdateTodoRequest updateTodoRequest, HttpServletRequest httpServletRequest){
        UpdateTodoResponse todo = todoService.editTask(updateTodoRequest, id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @PostMapping("/finished/{id}")
    public ResponseEntity<?> markAsDone(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String todo = todoService.markAsDone(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/todos")
    public ResponseEntity<?> viewAllTodos(HttpServletRequest httpServletRequest){
        List<Todo> list = todoService.viewAllTodo();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(list)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/finished")
    public ResponseEntity<?> viewAllFinishedTodos(HttpServletRequest httpServletRequest){
        List<FinishedTodo> list = todoService.viewAllFinishedTodo();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(list)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findFinishedTodoById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        FinishedTodo todo = todoService.findFinishedTodoById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId, HttpServletRequest httpServletRequest){
        DeleteTodoResponse todo = todoService.deleteTodo(todoId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteFinishedTodo(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String todo = todoService.deleteFinishedTodo(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }




}
