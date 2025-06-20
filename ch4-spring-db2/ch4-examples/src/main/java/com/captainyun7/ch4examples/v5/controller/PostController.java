package com.captainyun7.ch4examples.v5.controller;

import com.captainyun7.ch4examples.v5.dto.comment.CommentSearchRequest;
import com.captainyun7.ch4examples.v5.dto.post.*;
import com.captainyun7.ch4examples.v5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(service.createPost(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPostById(id));
    }

    @GetMapping("/{id}/paging")
    public ResponseEntity<PostWithCommentsResponsePaging> getPostWithPaginatedComments(
            @PathVariable Long id
            , @ModelAttribute CommentSearchRequest commentRequest) {
        return ResponseEntity.ok(service.getPostWithPaginatedComments(id, commentRequest));
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
    public ResponseEntity<PostPageResponse> searchPosts(PostSearchRequest search) {
        return ResponseEntity.ok(service.search(search));
    }

}
