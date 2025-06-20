package com.captainyun7.ch4examples.v5.service;

import com.captainyun7.ch4examples.v5.domain.Post;
import com.captainyun7.ch4examples.v5.dto.comment.CommentPageResponse;
import com.captainyun7.ch4examples.v5.dto.comment.CommentSearchRequest;
import com.captainyun7.ch4examples.v5.dto.post.*;
import com.captainyun7.ch4examples.v5.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository repository;
    private final CommentService commentService;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();
        Post saved = repository.save(post);
        return PostResponse.from(saved);
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
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        return PostResponse.from(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }


}