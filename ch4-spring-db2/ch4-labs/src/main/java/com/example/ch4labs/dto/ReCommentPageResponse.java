package com.example.ch4labs.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCommentPageResponse {
    private long totalPages;
    private long totalElements;
    private int size;
    private int page;

    private List<ReCommentResponse> reComments;
}
