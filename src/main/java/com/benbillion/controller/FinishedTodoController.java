package com.benbillion.controller;

import com.benbillion.models.data.FinishedTodo;
import com.benbillion.services.FinishedTodoService;
import com.benbillion.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/finished")
public class FinishedTodoController {
    @Autowired
    FinishedTodoService finishedTodoService;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> markAsDone(@PathVariable Long id,
                                        HttpServletRequest httpServletRequest){
        String todo = finishedTodoService.markAsDone(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/all")
    public ResponseEntity<?> viewAllFinishedTodos(HttpServletRequest httpServletRequest){
        List<FinishedTodo> list = finishedTodoService.viewAllFinishedTodo();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(list)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> findFinishedTodoById(@PathVariable Long id,
                                                  HttpServletRequest httpServletRequest){
        FinishedTodo todo = finishedTodoService.findFinishedTodoById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFinishedTodo(@PathVariable Long id,
                                                HttpServletRequest httpServletRequest){
        String todo = finishedTodoService.deleteFinishedTodo(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(todo)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<?> deleteAllFinishedTodo(HttpServletRequest httpServletRequest){
        String response = finishedTodoService.deleteAllFinishedTodo();
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