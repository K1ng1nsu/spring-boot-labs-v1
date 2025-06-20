package com.example.ch4codeyourself.v4.controller;

import com.example.ch4codeyourself.v4.dto.post.*;
import com.example.ch4codeyourself.v4.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/posts")
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(service.createPost(request));
    }


    // /posts?keyword=스프링&page=1&size=10
    @GetMapping
    public ResponseEntity<PostPageResponse> getAll(PostSearchRequest search) {
        return ResponseEntity.ok(service.getAllPosts(search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        return ResponseEntity.ok(service.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
