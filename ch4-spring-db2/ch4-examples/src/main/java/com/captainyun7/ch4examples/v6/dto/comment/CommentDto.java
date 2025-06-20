package com.captainyun7.ch4examples.v6.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    
    // CommentResponse로 변환하는 메서드
    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .id(id)
                .content(content)
                .author(author)
                .createdAt(createdAt)
                .build();
    }
} 