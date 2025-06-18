package com.example.ch4labs.dto;

import com.example.ch4labs.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private long id;
    private String title;
    private String content;
    private String author;
    private String bookTitle;
    private String bookAuthor;
    private int rating;


    public static ReviewResponse from(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setTitle(review.getTitle());
        reviewResponse.setContent(review.getContent());
        reviewResponse.setAuthor(review.getAuthor());
        reviewResponse.setBookTitle(review.getBookTitle());
        reviewResponse.setBookAuthor(review.getBookAuthor());
        reviewResponse.setRating(review.getRating());
        return reviewResponse;
    }
}
