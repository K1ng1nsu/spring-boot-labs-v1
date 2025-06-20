package com.example.ch4labs.dto.review;

import com.example.ch4labs.dto.comment.CommentPageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAndCommentResponse {
    private ReviewResponse reviewResponse;
    private CommentPageResponse comments;

    public static ReviewAndCommentResponse from(ReviewResponse reviewResponse, CommentPageResponse commentPageResponse) {
        ReviewAndCommentResponse reviewAndCommentResponse = new ReviewAndCommentResponse();
        reviewAndCommentResponse.setReviewResponse(reviewResponse);
        reviewAndCommentResponse.setComments(commentPageResponse);
        return reviewAndCommentResponse;
    }
}
