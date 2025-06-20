package com.example.ch4labs.dto.comment;

import com.example.ch4labs.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPageResponse {
    private long totalElements;
    private long totalPages;
    private int size;
    private int page;

    private List<CommentResponse> content;


    public static CommentPageResponse from(Page<Comment> commentPage) {
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.setContent(commentPage.getContent().stream().map(CommentResponse::from).collect(Collectors.toList()));
        commentPageResponse.setTotalElements(commentPage.getTotalElements());
        commentPageResponse.setTotalPages(commentPage.getTotalPages());
        commentPageResponse.setSize(commentPage.getSize());
        commentPageResponse.setPage(commentPage.getNumber());


        return commentPageResponse;
    }
}
