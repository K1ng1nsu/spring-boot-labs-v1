package com.example.ch4codeyourself.v3.service;

import com.example.ch4codeyourself.v3.domain.Post;
import com.example.ch4codeyourself.v3.dto.*;
import com.example.ch4codeyourself.v3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    // mapper -> JPA repository
    private final PostRepository postRepository;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();
        Post saved = postRepository.save(post); // ???
        return PostResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public PostPageResponse getAllPosts(PostSearchRequest search) {
        Pageable pageable = PageRequest.of(search.getPage(), search.getSize());

        // if author is not null -> findByAuthor
        // if keyword is not null -> findByTitleContaining

        Page<PostResponse> page;

        if (search.getKeyword() != null && search.getAuthor() != null) {
//            page = postRepository.findByTitleContainsAndAuthor(search.getKeyword(), search.getAuthor(), pageable).map(PostResponse::from);
            page = postRepository.searchByAuthorAndTitle(search.getAuthor(), search.getKeyword(), pageable).map(PostResponse::from);
        } else if (search.getKeyword() != null) {
            page = postRepository.findByTitleContaining(search.getKeyword(), pageable)
                    .map(PostResponse::from);
        } else if (search.getAuthor() != null) {
            page = postRepository.findByAuthor(search.getAuthor(), pageable).map(PostResponse::from);
        } else if (search.getCreatedAt() != null) {
//            page = postRepository.findByCreatedAtAfter(search.getCreatedAt(), pageable).map(PostResponse::from);
//            page = postRepository.searchByCreatedAtAfter(search.getCreatedAt(), pageable).map(PostResponse::from);
            page = postRepository.searchByCreatedAtWithQueryDSL(search.getCreatedAt(), pageable).map(PostResponse::from);
        } else {
            page = postRepository.findAll(pageable).map(PostResponse::from);
        }

        //SELECT

        return PostPageResponse.from(page.getContent(), search, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        return postRepository.findById(id)
                .map(PostResponse::from)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        // 트랜잭션이 끝날 때.
        // 변경이 일어나면 dirty checking -> SQL.

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        return PostResponse.from(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}