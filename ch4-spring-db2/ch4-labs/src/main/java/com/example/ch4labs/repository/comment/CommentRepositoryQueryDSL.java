package com.example.ch4labs.repository.comment;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.dto.comment.CommentSearchRequest;
import org.springframework.data.domain.Page;

public interface CommentRepositoryQueryDSL {
    Page<Comment> searchCommentByReviewId(CommentSearchRequest commentSearchRequest, long reviewId);
}
