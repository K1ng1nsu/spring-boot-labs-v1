package com.example.ch4labs.repository.review;

import com.example.ch4labs.domain.Review;
import com.example.ch4labs.dto.review.ReviewSearchRequest;
import org.springframework.data.domain.Page;

public interface ReviewRepositoryQueryDSL {
    Page<Review> search(ReviewSearchRequest reviewSearchRequest);

}
