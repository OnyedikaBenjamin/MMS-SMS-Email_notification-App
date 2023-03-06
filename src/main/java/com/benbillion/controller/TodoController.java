package com.benbillion.controller;

import com.benbillion.dtos.*;
import com.benbillion.models.data.Comment;
import com.benbillion.models.data.Todo;
import com.benbillion.services.EmailSenderService;
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
public class TodoController {
    private TodoService todoService;

//    private EmailSenderService emailSenderService;

    public TodoController(TodoService todoService,
                          EmailSenderService emailSenderService){
        this.todoService=todoService;
//        this.emailSenderService=emailSenderService;
    }
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

    @GetMapping("/all")
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
    @GetMapping("/comments/{todoId}")
    public ResponseEntity<?> viewComments(HttpServletRequest httpServletRequest, @PathVariable Long todoId){
        List<Comment> allComments = todoService.showTodoComments(todoId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(allComments)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{todoId}")
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

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllTodos(HttpServletRequest httpServletRequest){
        String response = todoService.deleteAllTodo();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

}