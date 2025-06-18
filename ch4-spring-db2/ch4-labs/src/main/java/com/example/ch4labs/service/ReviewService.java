package com.example.ch4labs.service;

import com.example.ch4labs.domain.Review;
import com.example.ch4labs.dto.ReviewCreateRequest;
import com.example.ch4labs.dto.ReviewResponse;
import com.example.ch4labs.dto.ReviewUpdateRequest;
import com.example.ch4labs.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        Review save = reviewRepository.save(reviewCreateRequest.toDomain());

        return ReviewResponse.from(save);
    }


    public List<ReviewResponse> findAll() {
        List<Review> all = reviewRepository.findAll();

        return all.stream().map(ReviewResponse::from).collect(Collectors.toList());
    }

    public ReviewResponse update(long id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = new Review();
        review.setId(id);
        review.setTitle(reviewUpdateRequest.getTitle());
        review.setContent(reviewUpdateRequest.getContent());
        review.setAuthor(reviewUpdateRequest.getAuthor());
        review.setBookTitle(reviewUpdateRequest.getBookTitle());
        review.setBookAuthor(reviewUpdateRequest.getBookAuthor());
        review.setRating(reviewUpdateRequest.getRating());

        Review updated = reviewRepository.save(review);

        return ReviewResponse.from(updated);
    }

    public void delete(long id) {
        Review reviewById = getReviewById(id);
        reviewRepository.delete(reviewById);
    }

    public Review getReviewById(long id) {
        return reviewRepository.findById(id).orElse(null);
    }
}
