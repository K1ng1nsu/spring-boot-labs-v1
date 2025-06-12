package com.captainyun7.ch2examples._03_rest_controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/v1")
public class RestController01 {

    @GetMapping("/hello")
    public String hello() {
        return "안녕";
    }

    @GetMapping("/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        Post post = new Post("샘플 게시글", "RestController 예제입니다.");
        post.setId(postId);
        return post;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("첫번째 게시글", "안녕하세요, 첫번째 게시글입니다."));
        posts.add(new Post("두번째 게시글", "안녕하세요, 두번째 게시글입니다."));
        posts.add(new Post("세번째 게시글", "안녕하세요, 세번째 게시글입니다."));
        return posts;
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        post.setId(1L);
        return post;
    }
} 