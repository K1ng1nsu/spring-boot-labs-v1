package com.example.ch4codeyourself.v4.controller;

import com.example.ch4codeyourself.v4.dto.comment.CommentCreateRequest;
import com.example.ch4codeyourself.v4.dto.comment.CommentUpdateRequest;
import com.example.ch4codeyourself.v4.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
@RequestMapping("/api/v4")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> saveComment(@PathVariable long postId, @RequestBody CommentCreateRequest commentCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, commentCreateRequest));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable long postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findCommentsByPostId(postId, page, size));
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
