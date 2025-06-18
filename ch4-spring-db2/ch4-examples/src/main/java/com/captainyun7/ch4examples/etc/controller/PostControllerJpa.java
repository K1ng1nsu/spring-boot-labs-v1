package com.captainyun7.ch4examples.etc.controller;

import com.captainyun7.ch4examples.etc.domain.Post;
import com.captainyun7.ch4examples.etc.service.PostServiceJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jpa/posts")
@RequiredArgsConstructor
public class PostControllerJpa {

    private final PostServiceJpa postService;

    @PostMapping
    public void create(@RequestBody Post post) {
        postService.save(post);
    }

    @GetMapping("/{id}")
    public Post getOne(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping
    public List<Post> getAll() {
        return postService.findAll();
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post post) {
        return postService.update(id, post.getTitle(), post.getBody());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}
