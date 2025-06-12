package com.captainyun7.ch2examples._03_rest_controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/v2")
public class RestController02 {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = new Post("샘플 게시글", "RestController 예제입니다.");
        post.setId(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("첫번째 게시글", "안녕하세요, 첫번째 게시글입니다."));
        posts.add(new Post("두번째 게시글", "안녕하세요, 두번째 게시글입니다."));
        posts.add(new Post("세번째 게시글", "안녕하세요, 세번째 게시글입니다."));
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        post.setId(1L);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/201")
    public ResponseEntity<String> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body("created!");
    }

    @DeleteMapping("/204")
    public ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/400")
    public ResponseEntity<String> badRequest() {
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @GetMapping("/401")
    public ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
    }

    @GetMapping("/403")
    public ResponseEntity<String> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
    }

    @GetMapping("/404")
    public ResponseEntity<String> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("데이터를 찾을 수 없습니다.");
    }

    @GetMapping("/500")
    public ResponseEntity<String> serverError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에 오류가 발생했습니다.");
    }

    @PostMapping("/location")
    public ResponseEntity<String> createPost() {
        URI location = URI.create("/rest/v2/posts");
        return ResponseEntity.status(HttpStatus.FOUND).location(location).body("게시글이 생성되었습니다.");
    }

    @GetMapping("/plain-text")
    public ResponseEntity<String> plainText() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("이건 텍스트입니다.");
    }

    @GetMapping("/no-cache")
    public ResponseEntity<String> noCache() {
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .body("항상 최신 데이터를 받아야 합니다.");
    }



}
