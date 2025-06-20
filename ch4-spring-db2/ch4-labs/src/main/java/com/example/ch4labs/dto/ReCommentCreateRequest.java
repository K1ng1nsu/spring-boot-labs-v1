package com.example.ch4labs.dto;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.domain.ReComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCommentCreateRequest {
    private String content;
    private String author;


    public ReComment toDomain(Comment comment) {
        ReComment reComment = new ReComment();
        reComment.setContent(content);
        reComment.setAuthor(author);
        reComment.setComment(comment);
        return reComment;
    }
}
