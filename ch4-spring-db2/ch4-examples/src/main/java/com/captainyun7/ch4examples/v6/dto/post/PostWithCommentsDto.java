package com.captainyun7.ch4examples.v6.dto.post;

import com.captainyun7.ch4examples.v6.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostWithCommentsDto {
    private Long id;
    private String title;
    private String body;
    private String author;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments = new ArrayList<>();
    
    // 게시글 정보만 담는 생성자 (JPQL 생성자 표현식에서 사용)
    public PostWithCommentsDto(Long id, String title, String body, String author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
    }
    
    // PostWithCommentsResponse로 변환하는 메서드
    public PostWithCommentsResponse toResponse() {
        PostResponse postResponse = new PostResponse(id, title, body, author, createdAt);
        return new PostWithCommentsResponse(postResponse, comments);
    }
} 