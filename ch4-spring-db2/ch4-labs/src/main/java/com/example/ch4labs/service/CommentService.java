package com.example.ch4labs.service;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.domain.Review;
import com.example.ch4labs.dto.comment.CommentPageResponse;
import com.example.ch4labs.dto.comment.CommentResponse;
import com.example.ch4labs.dto.comment.CommentUpdateRequest;
import com.example.ch4labs.dto.comment.CommentCreateRequest;
import com.example.ch4labs.dto.comment.CommentSearchRequest;
import com.example.ch4labs.repository.comment.CommentRepository;
import com.example.ch4labs.repository.review.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public CommentResponse createComment(long reviewId, CommentCreateRequest commentCreateRequest) {

        getReviewById(reviewId);

        Comment saved = commentRepository.save(commentCreateRequest.toDomain(reviewId));


        return CommentResponse.from(saved);
    }

    public CommentResponse updateComment(long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment commentById = getCommentById(commentId);
        commentById.setContent(commentUpdateRequest.getContent());
        Comment saved = commentRepository.save(commentById);
        return CommentResponse.from(saved);
    }

    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public CommentPageResponse findAllByReviewId(long reviewId, CommentSearchRequest commentSearchRequest) {
        getReviewById(reviewId);

        Page<Comment> comments = commentRepository.searchCommentByReviewId(commentSearchRequest, reviewId);


        return CommentPageResponse.from(comments);
    }


    private Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    private Review getReviewById(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review not found"));
    }


}
