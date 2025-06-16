package com.captainyun7.ch4examples.v1.service;

import com.captainyun7.ch4examples.v1.domain.Post;
import com.captainyun7.ch4examples.v1.dto.*;
import com.captainyun7.ch4examples.v1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();
        repository.save(post);
        return PostResponse.from(post);
    }

//    public List<PostResponse> getAllPosts() {
//        return repository.findAll().stream()
//                .map(post -> PostResponse.from(post))
//                .toList();
//    }

    public PostResponse getPostById(Long id) {
        return repository.findById(id)
                .map(PostResponse::from)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = Optional.of(repository.findById(id))
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."))
                .get();
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        Post updated = repository.save(post);
        return PostResponse.from(updated);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public PostPageResponse search(PostSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<PostResponse> page = repository.findByTitleContaining(request.getKeyword(), pageable)
                .map(PostResponse::from);

        return PostPageResponse.from(page.getContent(), request, page.getTotalElements());
    }
}