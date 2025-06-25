package com.example.ch4labs.service;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.domain.ReComment;
import com.example.ch4labs.dto.*;
import com.example.ch4labs.repository.ReCommentRepository;
import com.example.ch4labs.repository.comment.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReCommentService {
    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;

    public ReCommentResponse createRecomment(long commentId, ReCommentCreateRequest reCommentCreateRequest) {
        Comment commentById = getCommentById(commentId);
        ReComment saved = reCommentRepository.save(reCommentCreateRequest.toDomain(commentById));
        return ReCommentResponse.from(saved);
    }


    public ReCommentResponse updateReComment(long recommentId, ReCommentUpdateRequest reCommentUpdateRequest) {
        ReComment reCommentById = getReCommentById(recommentId);
        reCommentById.setContent(reCommentUpdateRequest.getContent());
        ReComment saved = reCommentRepository.save(reCommentById);
        return ReCommentResponse.from(saved);
    }

    public void deleteReComment(long recommentId) {
        reCommentRepository.deleteById(recommentId);
    }

    public ReCommentPageResponse getReComments(long commentId, RecommentPageRequest recommentPageRequest) {

        Page<ReComment> reComments = reCommentRepository.searchRecommentByCommentId(commentId, recommentPageRequest);
        // 메소드 구현 해야함 06.23 10:10


        return ReCommentPageResponse.from(reComments);
    }

    public ReCommentResponse getReComment(long recommentId) {
        ReComment reCommentById = getReCommentById(recommentId);
        return ReCommentResponse.from(reCommentById);
    }

    private Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment NOT FOUND"));
    }

    private ReComment getReCommentById(long recommentId) {
        return reCommentRepository.findById(recommentId).orElseThrow(() -> new EntityNotFoundException("ReComment NOT FOUND"));
    }


}
