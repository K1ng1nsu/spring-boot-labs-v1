package com.example.ch4codeyourself.v5.dto.comment;


import com.example.ch4codeyourself.v5.domain.Comment;
import com.example.ch4codeyourself.v5.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String content;
    private String author;
    private Long parentId;


    public Comment toDomain(Post targetPost, Comment targetComment) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setPost(targetPost);
        comment.setParent(targetComment);

        return comment;
    }
}
