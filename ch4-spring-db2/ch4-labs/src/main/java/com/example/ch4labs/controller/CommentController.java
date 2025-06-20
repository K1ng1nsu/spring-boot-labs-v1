package com.example.ch4labs.controller;


import com.example.ch4labs.dto.comment.CommentUpdateRequest;
import com.example.ch4labs.dto.comment.CommentCreateRequest;
import com.example.ch4labs.dto.comment.CommentSearchRequest;
import com.example.ch4labs.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/reviews/{reviewId}/comments")
    public ResponseEntity<?> addComment(@PathVariable long reviewId, @RequestBody CommentCreateRequest commentCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(reviewId, commentCreateRequest));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, commentUpdateRequest));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/reviews/{reviewId}/comments")
    public ResponseEntity<?> getCommentsByReviewId(@PathVariable long reviewId, CommentSearchRequest commentSearchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAllByReviewId(reviewId,commentSearchRequest));
    }



}
