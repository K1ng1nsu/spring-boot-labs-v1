package com.example.ch4labs.dto;


import com.example.ch4labs.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {
    private String title;
    private String content;
    private String author;
    private String bookTitle;
    private String bookAuthor;
    private int rating;

    public Review toDomain() {
        Review review = new Review();
        review.setTitle(title);
        review.setContent(content);
        review.setAuthor(author);
        review.setBookTitle(bookTitle);
        review.setBookAuthor(bookAuthor);
        review.setRating(rating);
        return review;
    }
}
