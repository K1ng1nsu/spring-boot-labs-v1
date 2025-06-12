package com.captainyun7.ch2examples._04_3tiers_crud.controller;

import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostCreateRequest;
import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostResponse;
import com.captainyun7.ch2examples._04_3tiers_crud.dto.PostUpdateRequest;
import com.captainyun7.ch2examples._04_3tiers_crud.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(service.createPost(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(service.getAllPosts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(service.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
