package com.example.ch4codeyourself.v5.controller;

import com.example.ch4codeyourself.v5.dto.comment.CommentCreateRequest;
import com.example.ch4codeyourself.v5.dto.comment.CommentSearchRequest;
import com.example.ch4codeyourself.v5.dto.comment.CommentUpdateRequest;
import com.example.ch4codeyourself.v5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v5")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> saveComment(@PathVariable long postId, @RequestBody CommentCreateRequest commentCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, commentCreateRequest));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable long postId, CommentSearchRequest commentSearchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findCommentsByPostId(postId,commentSearchRequest));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, commentUpdateRequest));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
