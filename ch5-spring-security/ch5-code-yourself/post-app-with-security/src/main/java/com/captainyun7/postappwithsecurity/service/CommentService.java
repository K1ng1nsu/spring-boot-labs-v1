package com.captainyun7.postappwithsecurity.service;

import com.captainyun7.postappwithsecurity.domain.Comment;
import com.captainyun7.postappwithsecurity.domain.Post;
import com.captainyun7.postappwithsecurity.domain.User;
import com.captainyun7.postappwithsecurity.dto.comment.*;
import com.captainyun7.postappwithsecurity.repository.UserRepository;
import com.captainyun7.postappwithsecurity.repository.comment.CommentRepository;
import com.captainyun7.postappwithsecurity.repository.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final UserService userService;


    @Transactional
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));

        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다"));
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User userByUsername = userService.getUserByUsername(userDetails.getUsername());


        Comment comment = Comment.builder()
                .post(post)
                .parent(parent)
                .user(userByUsername)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);
        return CommentResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public CommentPageResponse getCommentsByPost(Long postId, CommentSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("createdAt").ascending()
        );

        if (request.isHierarchical()) {
            return getHierarchicalCommentsByPost(postId, pageable);
        } else {
            return getFlatCommentsByPost(postId, pageable);
        }
    }

    private CommentPageResponse getFlatCommentsByPost(Long postId, Pageable pageable) {
        Page<CommentResponse> page = commentRepository
                .findByPostId(postId, pageable)
                .map(CommentResponse::from);

        return CommentPageResponse.from(page);
    }

    private CommentPageResponse getHierarchicalCommentsByPost(Long postId, Pageable pageable) {
        // 1. 부모 댓글만 페이징 처리하여 가져옴
        Page<Comment> parentCommentsPage = commentRepository.findParentCommentsByPostId(postId, pageable);

        List<CommentResponse> hierarchicalComments = new ArrayList<>();

        // 2. 각 부모 댓글에 대해 자식 댓글을 모두 조회하여 계층 구조로 변환
        for (Comment parentComment : parentCommentsPage.getContent()) {
            CommentResponse parentResponse = CommentResponse.from(parentComment);

            // 3. 자식 댓글 조회 및 설정
            List<Comment> childComments = commentRepository.findByParentIdOrderByCreatedAtAsc(parentComment.getId());
            List<CommentResponse> childResponses = childComments.stream()
                    .map(CommentResponse::from)
                    .collect(Collectors.toList());

            parentResponse.setReplies(childResponses);
            hierarchicalComments.add(parentResponse);
        }

        // 4. 페이징 정보와 함께 응답 생성
        return CommentPageResponse.builder()
                .page(parentCommentsPage.getNumber())
                .size(parentCommentsPage.getSize())
                .totalElements(parentCommentsPage.getTotalElements())
                .totalPages(parentCommentsPage.getTotalPages())
                .comments(hierarchicalComments)
                .build();
    }

    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다." + commentId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userByUsername = userService.getUserByUsername(authentication.getName());

        if (!userByUsername.getUsername().equals(comment.getUser().getUsername())) {
            throw new RuntimeException("YOU CAN NOT UPDATE COMMENT THAT IS NOT YOURS");
        }


        comment.setContent(request.getContent());

        return CommentResponse.from(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userByUsername = userService.getUserByUsername(authentication.getName());

        if (!userByUsername.getUsername().equals(comment.getUser().getUsername())) {
            if (!userByUsername.getRole().equals("ADMIN")) {
                throw new RuntimeException("YOU CAN NOT DELETE COMMENT THAT IS NOT YOURS");
            }
        }


        commentRepository.delete(comment);
    }
}
