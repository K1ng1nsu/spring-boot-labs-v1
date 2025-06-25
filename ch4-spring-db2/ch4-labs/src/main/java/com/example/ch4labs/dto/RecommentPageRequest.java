package com.example.ch4labs.dto;

import com.example.ch4labs.enums.ReCommentSort;
import com.example.ch4labs.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommentPageRequest {
    private Integer page = 0;
    private Integer size = 5;

    private SortDirection sortDirection = SortDirection.ASC;
    private ReCommentSort reCommentSort = ReCommentSort.CREATED_AT;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }

}
