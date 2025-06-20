package com.captainyun7.ch4examples.v5.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String content;
    private String author;
    private Long parentId;
}
