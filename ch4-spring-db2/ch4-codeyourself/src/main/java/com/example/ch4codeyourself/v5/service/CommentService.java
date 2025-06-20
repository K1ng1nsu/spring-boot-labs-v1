package com.example.ch4codeyourself.v5.service;

import com.example.ch4codeyourself.v5.domain.Comment;
import com.example.ch4codeyourself.v5.domain.Post;
import com.example.ch4codeyourself.v5.dto.comment.*;
import com.example.ch4codeyourself.v5.repository.CommentRepository;
import com.example.ch4codeyourself.v5.repository.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(long postId, CommentCreateRequest commentCreateRequest) {
        Post targetPost = getPostById(postId);
        Comment targetComment = null;

        if (commentCreateRequest.getParentId() != null) {
            targetComment = getCommentById(commentCreateRequest.getParentId());
        }
        Comment savedComment = commentRepository.save(commentCreateRequest.toDomain(targetPost, targetComment));

        return CommentResponse.from(savedComment);
    }


    public CommentPageResponse findCommentsByPostId(long postId, CommentSearchRequest commentSearchRequest) {
        Pageable pageable = PageRequest.of(commentSearchRequest.getPage(), commentSearchRequest.getSize(), Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<Comment> commentsPage;
        if (commentSearchRequest.isHierarchy()) {
            // 계층구조
            commentsPage = commentRepository.findByPostIdAndParentIdNull(postId, pageable);
            CommentPageResponse result = CommentPageResponse.from(commentsPage);
            result.getComments().forEach(commentResponse -> {
                Pageable recommentPageable = PageRequest.of(commentSearchRequest.getInnerPage(), commentSearchRequest.getInnerSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
                Page<Comment> recomments = commentRepository.findByParentId(commentResponse.getId(), recommentPageable);
                CommentPageResponse recommentsResponse = CommentPageResponse.from(recomments);
                commentResponse.setReComments(recommentsResponse);
            });

            return result;

        } else {
            // 평면구조
            commentsPage = commentRepository.findByPostId(postId, pageable);

        }


        return CommentPageResponse.from(commentsPage);
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

    private Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("CAN NOT FOUND Comment"));
    }

//    private Comment getCommentByIdNullable(long commentId) {
//        return commentRepository.findById(commentId).orElse(null);
//    }
}
