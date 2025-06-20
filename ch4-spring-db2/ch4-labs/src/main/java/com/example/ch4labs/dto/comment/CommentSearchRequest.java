package com.example.ch4labs.dto.comment;


import com.example.ch4labs.enums.CommentSort;
import com.example.ch4labs.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSearchRequest {
    private int page = 0;
    private int size = 0;

    private CommentSort commentSort;
    private SortDirection sortDirection;
}
