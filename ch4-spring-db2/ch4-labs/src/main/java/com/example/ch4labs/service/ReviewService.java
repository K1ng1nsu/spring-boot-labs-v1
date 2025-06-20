package com.example.ch4labs.service;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.domain.Review;
import com.example.ch4labs.dto.comment.CommentPageResponse;
import com.example.ch4labs.dto.comment.CommentSearchRequest;
import com.example.ch4labs.dto.review.*;
import com.example.ch4labs.repository.comment.CommentRepository;
import com.example.ch4labs.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    public ReviewResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        Review save = reviewRepository.save(reviewCreateRequest.toDomain());

        return ReviewResponse.from(save);
    }


    public ReviewPageResponse findAll(ReviewSearchRequest reviewSearchRequest) {
        Page<Review> search = reviewRepository.search(reviewSearchRequest);

        return ReviewPageResponse.from(search, reviewSearchRequest.getPage());
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

    public ReviewAndCommentResponse findById(long reviewId, ReviewSingleRequest reviewSingleRequest) {
        Review reviewById = getReviewById(reviewId);
        Page<Comment> comments = null;
        CommentPageResponse commentPageResponse = null;
        if (reviewSingleRequest.isIncludeComments()) {
            //true
            CommentSearchRequest commentSearchRequest = new CommentSearchRequest();
            commentSearchRequest.setPage(reviewSingleRequest.getCommentPage());
            commentSearchRequest.setSize(reviewSingleRequest.getCommentSize());
            comments = commentRepository.searchCommentByReviewId(commentSearchRequest, reviewId);
        }
        if (comments != null) {
            commentPageResponse = CommentPageResponse.from(comments);
        }

        return ReviewAndCommentResponse.from(ReviewResponse.from(reviewById), commentPageResponse);
    }
}
