package com.captainyun7.ch4examples.v3.dto;

import com.captainyun7.ch4examples.v1.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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