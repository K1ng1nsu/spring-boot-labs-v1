package com.example.ch4codeyourself.v4.dto.post;

import com.example.ch4codeyourself.v4.domain.Post;
import com.example.ch4codeyourself.v4.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWithCommentsResponse {
    private PostResponse post;
    private List<CommentResponse> comments;

    public static PostWithCommentsResponse from(Post post) {
        PostWithCommentsResponse response = new PostWithCommentsResponse();
        response.setComments(post.getComments().stream().map(CommentResponse::from).collect(Collectors.toList()));
        response.setPost(PostResponse.from(post));

        return response;
    }
}
