package com.captainyun7.ch4examples.v4.service;

import com.captainyun7.ch4examples.v4.domain.Comment;
import com.captainyun7.ch4examples.v4.domain.Post;
import com.captainyun7.ch4examples.v4.dto.comment.CommentCreateRequest;
import com.captainyun7.ch4examples.v4.dto.comment.CommentPageResponse;
import com.captainyun7.ch4examples.v4.dto.comment.CommentResponse;
import com.captainyun7.ch4examples.v4.dto.comment.CommentUpdateRequest;
import com.captainyun7.ch4examples.v4.repository.comment.CommentRepository;
import com.captainyun7.ch4examples.v4.repository.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 생성
     */
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(request.getAuthor())
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }

    /**
     * 댓글 조회
     */
    @Transactional(readOnly = true)
    public CommentPageResponse getCommentsByPost(Long postId, Pageable pageable) {
        Page<CommentResponse> page = commentRepository
                .findByPostId(postId, pageable)
                .map(CommentResponse::from);

        return CommentPageResponse.from(page);
    }

    /**
     * 댓글 수정
     */
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다." + commentId));

        comment.setContent(request.getContent());

        return CommentResponse.from(comment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        commentRepository.delete(comment);
    }
}
