package com.example.ch3examples.dto;

import lombok.Data;

@Data
public class PostSearchRequest {
    private String keyword; // null or empty면 전체 검색
    private int page = 1;
    private int size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }
}
