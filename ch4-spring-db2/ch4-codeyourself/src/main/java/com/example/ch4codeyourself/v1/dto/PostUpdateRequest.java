package com.example.ch4codeyourself.v1.dto;

import com.example.ch4codeyourself.v1.domain.Post;
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