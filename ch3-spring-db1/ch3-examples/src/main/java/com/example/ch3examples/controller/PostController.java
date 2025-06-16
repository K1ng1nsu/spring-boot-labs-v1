package com.example.ch3examples.controller;

import com.example.ch3examples.dto.*;
import com.example.ch3examples.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(service.createPost(request));
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAll() {
//        return ResponseEntity.ok(service.getAllPosts());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
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

    @GetMapping
    public ResponseEntity<PostPageResponse> list(PostSearchRequest search) {
        return ResponseEntity.ok(service.getPosts(search));
    }


}
