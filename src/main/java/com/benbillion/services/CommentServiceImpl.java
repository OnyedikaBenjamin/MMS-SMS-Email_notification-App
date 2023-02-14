package com.benbillion.services;

import com.benbillion.dtos.CreateCommentRequest;
import com.benbillion.dtos.EditCommentRequest;
import com.benbillion.models.data.Comment;
import com.benbillion.models.data.Todo;
import com.benbillion.models.repository.CommentRepository;
import com.benbillion.models.repository.TodoRepository;
import exceptions.GenericHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    CommentRepository commentRepo;

    @Override
    public String createComment(Long todoId,
                                CreateCommentRequest createCommentRequest) {
        Comment comment = new Comment();
        Todo queriedTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GenericHandlerException("Todo queried does not exist"));
        try {
            comment.setTodo(queriedTodo);
            comment.setBody(createCommentRequest.getBody());
            commentRepo.save(comment);
            todoRepository.save(queriedTodo);
        } catch (GenericHandlerException e) {
            throw new RuntimeException();
        }
        return "Comment successfully created";
    }
    @Override
    public String editComment(Long todoId,
                              Long commentId,
                              EditCommentRequest editCommentRequest) {
        Todo queriedTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GenericHandlerException("Todo queried does not exist"));
        for(Comment comment : queriedTodo.getComments()){
            if(comment.getId().equals(commentId)){
                comment.setBody(editCommentRequest.getBody());
            }
        }
        Comment commentToEdit = commentRepo.findAll().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        commentToEdit.setBody(editCommentRequest.getBody());
        commentRepo.save(commentToEdit);
        return "Comment successfully edited";
    }
    @Override
    public List<Comment> viewAllComments() {
        List<Comment> repoComments = commentRepo.findAll();
        return new ArrayList<>(repoComments);
    }
    @Override
    public String deleteComment(Long todoId,
                                Long commentId) {
        Todo queriedTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GenericHandlerException("Todo queried does not exist"));
//        Comment commentToDelete = queriedTodo.getComments().stream()
//                .filter(comment -> comment.getId().equals(commentId))
//                .findFirst()
//                .orElseThrow(RuntimeException::new);
        Comment commentToDelete = commentRepo.findById(commentId).get();
        commentRepo.delete(commentToDelete);
        return "Comment successfully deleted";
    }

}