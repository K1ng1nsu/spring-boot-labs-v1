package com.captainyun7.ch4examples.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private String keyword = "";
    private int page = 0;
    private int size = 10;
}
