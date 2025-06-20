package com.captainyun7.ch4examples.v5.controller;

import com.captainyun7.ch4examples.v5.dto.comment.*;
import com.captainyun7.ch4examples.v5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * POST /api/v1/posts/{postId}/comments
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request) {

        CommentResponse response = commentService.createComment(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 댓글 조회 (계층형/평면형 선택 가능)
     * GET /api/v1/posts/{postId}/comments
     */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentPageResponse> getComments(
            @PathVariable Long postId,
            @ModelAttribute CommentSearchRequest request) {

        CommentPageResponse response = commentService.getCommentsByPost(postId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 수정
     * PUT /api/v1/comments/{commentId}
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request) {

        CommentResponse response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 삭제
     * DELETE /api/v1/comments/{commentId}
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}