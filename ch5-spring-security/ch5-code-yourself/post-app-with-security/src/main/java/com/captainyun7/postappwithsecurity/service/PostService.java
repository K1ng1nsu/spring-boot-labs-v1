package com.captainyun7.postappwithsecurity.service;

import com.captainyun7.postappwithsecurity.domain.Post;
import com.captainyun7.postappwithsecurity.domain.User;
import com.captainyun7.postappwithsecurity.dto.comment.CommentPageResponse;
import com.captainyun7.postappwithsecurity.dto.comment.CommentSearchRequest;
import com.captainyun7.postappwithsecurity.dto.post.*;
import com.captainyun7.postappwithsecurity.repository.post.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository repository;
    private final CommentService commentService;
    private final UserService userService;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            User userByUsername = userService.getUserByUsername(userDetails.getUsername());
            post.setUser(userByUsername);
            Post saved = repository.save(post);
            return PostResponse.from(saved);

        } else {
            throw new RuntimeException("YOU ARE NOT LOGIN");
        }
    }

    @Transactional(readOnly = true)
    public PostPageResponse search(PostSearchRequest request) {
        Page<PostResponse> page = repository.search(request);
        return PostPageResponse.from(page.getContent(), request, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostWithCommentsResponse getPostById(Long id) {
        return repository.findByIdWithComments(id)
                .map(PostWithCommentsResponse::from)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public PostWithCommentsResponsePaging getPostWithPaginatedComments(Long postId, CommentSearchRequest commentRequest) {
        // 1. 게시글 자체 정보만 조회
        Post post = repository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        // 2. 댓글 서비스를 통해 계층형 페이징된 댓글 조회
        CommentPageResponse commentPageResponse = commentService.getCommentsByPost(postId, commentRequest);

        // 3. 게시글과 페이징된 댓글 정보를 합쳐서 응답 생성
        return PostWithCommentsResponsePaging.builder()
                .post(PostResponse.from(post))
                .commentPage(commentPageResponse) // 페이징된 댓글 정보
                .build();
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        boolean equals = post.getUser().getUsername().equals(name);
        if (equals) {
            post.setTitle(request.getTitle());
            post.setBody(request.getBody());

            return PostResponse.from(post);
        } else {
            throw new RuntimeException("YOU CAN NOT UPDATE POST THAT NOT YOURS");
        }
    }

    public void deletePost(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Post post = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다."));

        if (post.getUser().getUsername().equals(name)) {
            repository.deleteById(id);
        } else {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                repository.deleteById(id);
            } else {
                throw new RuntimeException("YOU CANNOT DELETE POST THAT NOT YOURS");
            }
        }

    }


}