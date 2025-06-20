package com.example.ch4labs.dto.comment;

import com.example.ch4labs.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    private String content;
    private String author;

    public Comment toDomain(long reviewId) {
        Comment comment = new Comment();
        comment.setReviewId(reviewId);
        comment.setContent(content);
        comment.setAuthor(author);

        return comment;
    }
}
