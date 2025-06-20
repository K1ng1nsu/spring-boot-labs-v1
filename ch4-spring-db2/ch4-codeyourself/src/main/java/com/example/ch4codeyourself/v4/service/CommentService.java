package com.example.ch4codeyourself.v4.service;

import com.example.ch4codeyourself.v4.domain.Comment;
import com.example.ch4codeyourself.v4.domain.Post;
import com.example.ch4codeyourself.v4.dto.comment.CommentCreateRequest;
import com.example.ch4codeyourself.v4.dto.comment.CommentPageResponse;
import com.example.ch4codeyourself.v4.dto.comment.CommentResponse;
import com.example.ch4codeyourself.v4.dto.comment.CommentUpdateRequest;
import com.example.ch4codeyourself.v4.repository.comment.CommentRepository;
import com.example.ch4codeyourself.v4.repository.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(long postId, CommentCreateRequest commentCreateRequest) {
        Post targetPost = getPostById(postId);
        Comment savedComment = commentRepository.save(commentCreateRequest.toDomain(targetPost));

        return CommentResponse.from(savedComment);
    }


    public CommentPageResponse findCommentsByPostId(long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);

        return CommentPageResponse.from(commentsPage, pageable);
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public CommentResponse updateComment(long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        comment.setContent(commentUpdateRequest.getContent());
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }


    private Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("CAN NOT FOUND POST"));
    }
}
