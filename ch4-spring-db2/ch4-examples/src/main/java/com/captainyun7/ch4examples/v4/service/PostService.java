package com.captainyun7.ch4examples.v4.service;

import com.captainyun7.ch4examples.v4.domain.Post;
import com.captainyun7.ch4examples.v4.dto.post.*;
import com.captainyun7.ch4examples.v4.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository repository;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();
        Post saved = repository.save(post);
        return PostResponse.from(saved);
    }

//    @Transactional(readOnly = true)
//    public PostPageResponse search(PostSearchRequest request) {
//        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
//        Page<PostResponse> page = repository.findByTitleContaining(request.getKeyword(), pageable)
//                .map(PostResponse::from);
//
//        return PostPageResponse.from(page.getContent(), request, page.getTotalElements());
//    }

    @Transactional(readOnly = true)
    public PostPageResponse search2(PostSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("createdAt").descending());

        Page<Post> posts = null;
        // 조건 조합 분기
        if (request.getKeyword() != null && request.getAuthor() != null) {
            posts = repository.findByAuthorAndTitleContaining(request.getAuthor(), request.getKeyword(), pageable);
            // posts = repository.searchByAuthorAndTitle(request.getAuthor(), request.getKeyword(), pageable);
        } else if (request.getKeyword() != null) {
            posts = repository.findByTitleContaining(request.getKeyword(), pageable);
        } else if (request.getAuthor() != null) {
            posts = repository.findByAuthor(request.getAuthor(), pageable);
        } else if (request.getCreatedAt() != null) {
            // posts = repository.findByCreatedAtAfter(request.getCreatedAt(), pageable);
            posts = repository.searchByCreatedAtWithQueryDSL(request.getCreatedAt(), pageable);
            // posts = repository.searchByCreatedAfter(request.getCreatedAt(), pageable);
        } else {
            posts = repository.findAll(pageable); // 조건 없으면 전체 조회
        }

        Page<PostResponse> page = posts.map(PostResponse::from);

        return PostPageResponse.from(page.getContent(), request, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostPageResponse search(PostSearchRequest request) {
        Page<PostResponse> page = repository.search(request);
        return PostPageResponse.from(page.getContent(), request, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        return repository.findById(id)
                .map(PostResponse::from)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
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