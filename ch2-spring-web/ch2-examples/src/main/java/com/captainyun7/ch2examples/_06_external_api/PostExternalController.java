package com.captainyun7.ch2examples._06_external_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external-api/posts")
@RequiredArgsConstructor
public class PostExternalController {

    private final PostExternalService postService;

    // 외부 API에서 가져와 메모리에 저장
    @PostMapping("/sync")
    public ResponseEntity<String> syncPosts() {
        boolean isStored = postService.fetchAndStorePosts();
        if (!isStored) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.ok("게시글 동기화 완료");
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }
}
