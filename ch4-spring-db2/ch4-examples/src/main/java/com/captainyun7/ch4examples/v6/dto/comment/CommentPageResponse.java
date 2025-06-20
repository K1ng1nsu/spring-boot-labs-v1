package com.captainyun7.ch4examples.v6.dto.comment;

import com.captainyun7.ch4examples.v6.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentPageResponse {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<com.captainyun7.ch4examples.v6.dto.comment.CommentResponse> comments;

    public static CommentPageResponse from(Page<CommentResponse> pageData) {
        return CommentPageResponse.builder()
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .comments(pageData.getContent())
                .build();
    }
}