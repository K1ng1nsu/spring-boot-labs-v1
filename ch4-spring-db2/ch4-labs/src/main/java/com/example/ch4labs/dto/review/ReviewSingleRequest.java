package com.example.ch4labs.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSingleRequest {
    private boolean includeComments = false;
    private int commentPage = 0;
    private int commentSize = 5;
}
