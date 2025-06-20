package com.captainyun7.ch4examples.v4.dto.post;

import com.captainyun7.ch4examples.v4.domain.Post;
import com.captainyun7.ch4examples.v4.dto.comment.CommentResponse;
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
