package com.example.ch3examples.dto;

import com.example.ch3examples.domain.Post;
import lombok.Data;

@Data
public class PostUpdateRequest {
    private String title;
    private String body;

    public Post toDomain() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setBody(this.body);
        return post;
    }
}