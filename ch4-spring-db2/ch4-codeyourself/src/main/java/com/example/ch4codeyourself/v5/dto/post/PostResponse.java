package com.example.ch4codeyourself.v5.dto.post;


import com.example.ch4codeyourself.v5.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String body;

    private String author;
    private LocalDateTime createAt;


    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getAuthor(),
                post.getCreatedAt()
        );
    }
}
