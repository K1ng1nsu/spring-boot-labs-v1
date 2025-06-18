package com.captainyun7.ch4examples.v3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private String keyword;
    private String author;
    private LocalDateTime createdAt;
    private String sort;
    private int page = 0;
    private int size = 10;
}
