package com.captainyun7.ch2examples._04_3tiers_crud.service;

import com.captainyun7.ch2examples._04_3tiers_crud.domain.Post;
import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostCreateRequest;
import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostResponse;
import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostUpdateRequest;
import com.captainyun7.ch2examples._04_3tiers_crud.exception.PostNotFoundException;
import com.captainyun7.ch2examples._04_3tiers_crud.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostResponse createPost(PostCreateRequest request) {
        Post post = new Post(null, request.getTitle(), request.getBody());
        Post saved = repository.save(post);
        return toResponse(saved);
    }

    public PostResponse getPost(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return toResponse(post);
    }

    public List<PostResponse> getAllPosts() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        return toResponse(post);
    }

    public void deletePost(Long id) {
        repository.delete(id);
    }

    private PostResponse toResponse(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getBody());
    }
}