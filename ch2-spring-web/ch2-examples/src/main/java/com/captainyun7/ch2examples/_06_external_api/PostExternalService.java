package com.captainyun7.ch2examples._06_external_api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostExternalService {

    private final WebClient webClient;
    private final Map<Long, Post> postMap = new HashMap<>();

    public PostExternalService() {
        this.webClient = WebClient.create("https://jsonplaceholder.typicode.com");
    }

    // 외부 API에서 데이터 가져와서 저장
    public boolean fetchAndStorePosts() {
        Post[] posts = webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToMono(Post[].class)
                .block();

        if (posts != null) {
            for (Post post : posts) {
                postMap.put(post.getId(), post);
            }
            return true;
        }
        return false;
    }

    public List<PostDto> getAllPosts() {
        return postMap.values().stream()
                .map(p -> new PostDto(p.getId(), p.getTitle(), p.getBody()))
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        Post post = postMap.get(id);
        if (post == null) return null;
        return new PostDto(post.getId(), post.getTitle(), post.getBody());
    }
}
