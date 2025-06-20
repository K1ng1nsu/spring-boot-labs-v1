package com.captainyun7.ch4examples.v5.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostPageResponse {
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;
    private List<com.captainyun7.ch4examples.v5.dto.post.PostResponse> posts;

    public static PostPageResponse from(List<PostResponse> posts, PostSearchRequest search, Long count) {
        int totalPages = (int) Math.ceil((double) count / search.getSize());
        return new PostPageResponse(
                search.getPage(),
                search.getSize(),
                count,
                totalPages,
                posts
        );
    }
}
