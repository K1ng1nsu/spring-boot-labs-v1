package com.example.ch4labs.dto.review;

import com.example.ch4labs.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPageResponse {
    private List<ReviewResponse> content;
    private long totalElement;
    private long totalPages;
    private int size;
    private int page;


    public static ReviewPageResponse from(Page<Review> reviewPage, int page) {
        ReviewPageResponse reviewPageResponse = new ReviewPageResponse();
        reviewPageResponse.setContent(reviewPage.getContent().stream().map(ReviewResponse::from).collect(Collectors.toList()));
        reviewPageResponse.setTotalElement(reviewPage.getTotalElements());
        reviewPageResponse.setTotalPages(reviewPage.getTotalPages());
        reviewPageResponse.setSize(reviewPage.getSize());
        reviewPageResponse.setPage(page);
        return reviewPageResponse;

    }

}
