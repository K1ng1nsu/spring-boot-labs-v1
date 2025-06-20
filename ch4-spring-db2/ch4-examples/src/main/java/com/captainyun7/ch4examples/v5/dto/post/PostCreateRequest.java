package com.captainyun7.ch4examples.v5.dto.post;

import com.captainyun7.ch4examples.v5.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    private String title;
    private String body;

    public Post toDomain() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setBody(this.body);
        return post;
    }
}