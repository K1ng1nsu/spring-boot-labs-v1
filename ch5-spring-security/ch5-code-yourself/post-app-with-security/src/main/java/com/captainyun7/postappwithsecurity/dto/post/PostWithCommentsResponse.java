package com.captainyun7.postappwithsecurity.dto.post;

import com.captainyun7.postappwithsecurity.domain.Post;
import com.captainyun7.postappwithsecurity.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithCommentsResponse {

    private PostResponse post;
    private List<CommentResponse> comments;

    public static PostWithCommentsResponse from(Post post) {
        return PostWithCommentsResponse.builder()
                .post(PostResponse.from(post))
                .comments(
                        post.getComments().stream()
                                .map(CommentResponse::from)
                                .toList()
                )
                .build();
    }
}
