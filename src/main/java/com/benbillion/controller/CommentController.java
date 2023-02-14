package com.benbillion.controller;

import com.benbillion.dtos.*;
import com.benbillion.models.data.Comment;
import com.benbillion.services.CommentService;
import com.benbillion.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comment")

public class CommentController {
        @Autowired
        CommentService commentService;

        @PostMapping("/create/{todoId}")
        public ResponseEntity<?> createComment(@PathVariable Long todoId,
                                               @RequestBody CreateCommentRequest createCommentRequest,
                                               HttpServletRequest httpServletRequest){
            String CreateResponse = commentService.createComment(todoId, createCommentRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .timeStamp(ZonedDateTime.now())
                    .data(CreateResponse)
                    .path(httpServletRequest.getRequestURI())
                    .statusCode(HttpStatus.OK.value())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
        @PutMapping("/edit/{todoId}/{commentId}")
        public ResponseEntity<?> editComment(@PathVariable Long todoId,
                                             @PathVariable Long commentId,
                                             @RequestBody EditCommentRequest editCommentRequest,
                                             HttpServletRequest httpServletRequest){
            String editResponse = commentService.editComment(todoId, commentId, editCommentRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .timeStamp(ZonedDateTime.now())
                    .data(editResponse)
                    .path(httpServletRequest.getRequestURI())
                    .statusCode(HttpStatus.OK.value())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }

        @GetMapping("/all")
        public ResponseEntity<?> viewComments(HttpServletRequest httpServletRequest){
            List<Comment> allComments = commentService.viewAllComments();
            ApiResponse apiResponse = ApiResponse.builder()
                    .timeStamp(ZonedDateTime.now())
                    .data(allComments)
                    .path(httpServletRequest.getRequestURI())
                    .statusCode(HttpStatus.OK.value())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{todoId}/{commentId}")
        public ResponseEntity<?> deleteComment(@PathVariable Long todoId,
                                               @PathVariable Long commentId,
                                               HttpServletRequest httpServletRequest){
            String deleteResponse = commentService.deleteComment(todoId, commentId);
            ApiResponse apiResponse = ApiResponse.builder()
                    .timeStamp(ZonedDateTime.now())
                    .data(deleteResponse)
                    .path(httpServletRequest.getRequestURI())
                    .statusCode(HttpStatus.OK.value())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }

    }