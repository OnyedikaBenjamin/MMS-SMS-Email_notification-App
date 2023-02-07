package com.benbillion.models.repository;

import com.benbillion.models.data.Comment;
import com.benbillion.models.data.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentsByTodo(Todo todo);
    List<Comment> findByTodoId(Long todoId);
}
