package com.captainyun7.postappwithsecurity.dto.post;

import com.captainyun7.postappwithsecurity.domain.Post;
import com.captainyun7.postappwithsecurity.dto.comment.CommentPageResponse;
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
