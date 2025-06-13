package com.captainyun7.ch2examples._06_external_api;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;
}