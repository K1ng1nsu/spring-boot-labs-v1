package com.example.ch4codeyourself.v4.dto.comment;

import com.example.ch4codeyourself.v4.domain.Comment;
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
    private int totalElement;
    private int totalPage;


    public static CommentPageResponse from(Page<Comment> commentsPage, Pageable pageable) {
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.setComments(commentsPage.getContent().stream().map(CommentResponse::from).collect(Collectors.toList()));
        commentPageResponse.setCurrentPage(pageable.getPageNumber());
        commentPageResponse.setSize(pageable.getPageSize());
        commentPageResponse.setTotalPage(commentsPage.getTotalPages());
        commentPageResponse.setTotalElement(commentsPage.getNumberOfElements());

        return commentPageResponse;
    }
}
