package com.example.ch4labs.dto;

import com.example.ch4labs.domain.ReComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCommentResponse {
    private long id;
    private long commentId;

    private String content;
    private String author;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReCommentResponse from(ReComment reComment) {
        ReCommentResponse reCommentResponse = new ReCommentResponse();

        reCommentResponse.setId(reComment.getId());
        reCommentResponse.setCommentId(reComment.getComment().getId());

        reCommentResponse.setContent(reComment.getContent());
        reCommentResponse.setAuthor(reComment.getAuthor());

        reCommentResponse.setCreatedAt(reComment.getCreatedAt());
        reCommentResponse.setUpdatedAt(reComment.getUpdatedAt());
        return reCommentResponse;
    }

}
