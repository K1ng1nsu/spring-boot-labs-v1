package com.example.ch4codeyourself.v5.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSearchRequest {
    private int page = 0;
    private int size = 10;


    private boolean hierarchy = true;
    private int innerPage = 0;
    private int innerSize = 5;
}
