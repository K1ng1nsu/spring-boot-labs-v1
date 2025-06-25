package com.example.ch4labs.dto;

import com.example.ch4labs.domain.ReComment;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCommentPageResponse {
    private long totalPages;
    private long totalElements;
    private int size;
    private int page;

    private List<ReCommentResponse> reComments;

    public static ReCommentPageResponse from(Page<ReComment> page) {
        ReCommentPageResponse response = new ReCommentPageResponse();
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setSize(page.getSize());
        response.setPage(page.getNumber());
        response.setReComments(page.getContent().stream().map(ReCommentResponse::from).collect(Collectors.toList()));

        return response;
    }
}
