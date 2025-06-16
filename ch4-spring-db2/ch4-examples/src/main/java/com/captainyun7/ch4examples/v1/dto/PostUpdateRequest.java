package com.captainyun7.ch4examples.v1.dto;

import com.captainyun7.ch4examples.v1.domain.Post;
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