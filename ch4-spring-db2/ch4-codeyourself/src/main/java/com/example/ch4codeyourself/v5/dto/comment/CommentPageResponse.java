package com.example.ch4codeyourself.v5.dto.comment;

import com.example.ch4codeyourself.v5.domain.Comment;
import com.example.ch4codeyourself.v5.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageResponse {
    private List<CommentResponse> comments;
    private int currentPage;
    private int size;
    private long totalElement;
    private int totalPage;


    public static CommentPageResponse from(Page<Comment> commentsPage) {
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.setComments(commentsPage.getContent().stream().map(CommentResponse::from).collect(Collectors.toList()));
        commentPageResponse.setCurrentPage(commentsPage.getNumber());
        commentPageResponse.setSize(commentsPage.getSize());
        commentPageResponse.setTotalPage(commentsPage.getTotalPages());
        commentPageResponse.setTotalElement(commentsPage.getTotalElements());

        return commentPageResponse;
    }
}
