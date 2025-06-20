package com.captainyun7.ch4examples.v5.dto.post;

import com.captainyun7.ch4examples.v5.domain.Post;
import com.captainyun7.ch4examples.v5.dto.comment.CommentPageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithCommentsResponsePaging {

    private PostResponse post;
    private CommentPageResponse commentPage;

    public static PostWithCommentsResponsePaging from(Post post, CommentPageResponse commentPage) {
        return PostWithCommentsResponsePaging.builder()
                .post(PostResponse.from(post))
                .commentPage(
                commentPage
                )
                .build();
    }
}
