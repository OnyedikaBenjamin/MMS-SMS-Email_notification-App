package com.benbillion.services;

import com.benbillion.dtos.CreateCommentRequest;
import com.benbillion.dtos.EditCommentRequest;
import com.benbillion.models.data.Comment;

import java.util.List;

public interface CommentService {
    String createComment(Long todoId, CreateCommentRequest createCommentRequest);
    String editComment(Long todoId, Long commentId,  EditCommentRequest editCommentRequest);
    List<Comment> viewAllComments();
    String deleteComment(Long todoId,  Long commentId);
}
