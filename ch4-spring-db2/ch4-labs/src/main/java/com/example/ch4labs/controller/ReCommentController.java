package com.example.ch4labs.controller;

import com.example.ch4labs.domain.ReComment;
import com.example.ch4labs.dto.ReCommentCreateRequest;
import com.example.ch4labs.dto.ReCommentUpdateRequest;
import com.example.ch4labs.dto.RecommentPageRequest;
import com.example.ch4labs.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReCommentController {
    private final ReCommentService reCommentService;

    @PostMapping("/comments/{commentId}/recomments")
    public ResponseEntity<?> createReComment(@PathVariable long commentId, @RequestBody ReCommentCreateRequest reCommentCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reCommentService.createRecomment(commentId, reCommentCreateRequest));
    }

    @PutMapping("/recomments/{recommentId}")
    public ResponseEntity<?> updateReComment(@PathVariable long recommentId, @RequestBody ReCommentUpdateRequest reCommentUpdateRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(reCommentService.updateReComment(recommentId, reCommentUpdateRequest));
    }

    @DeleteMapping("/recomments/{recommentId}")
    public ResponseEntity<?> deleteReComment(@PathVariable long recommentId) {
        reCommentService.deleteReComment(recommentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/comments/{commentId}/recomments")
    public ResponseEntity<?> getReComments(@PathVariable long commentId, RecommentPageRequest recommentPageRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(reCommentService.getReComments(commentId, recommentPageRequest));
    }

    @GetMapping("/recomments/{recommentId}")
    public ResponseEntity<?> getReComment(@PathVariable long recommentId) {

        return ResponseEntity.status(HttpStatus.OK).body(reCommentService.getReComment(recommentId));
    }

}
