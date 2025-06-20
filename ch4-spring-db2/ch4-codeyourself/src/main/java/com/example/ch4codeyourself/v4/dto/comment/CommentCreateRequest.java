package com.example.ch4codeyourself.v4.dto.comment;


import com.example.ch4codeyourself.v4.domain.Comment;
import com.example.ch4codeyourself.v4.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String content;
    private String author;


    public Comment toDomain(Post targetPost) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setPost(targetPost);
        return comment;
    }
}
