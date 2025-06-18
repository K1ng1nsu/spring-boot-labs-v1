package com.example.ch4labs.controller;


import com.example.ch4labs.dto.ReviewCreateRequest;
import com.example.ch4labs.dto.ReviewUpdateRequest;
import com.example.ch4labs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewSerive;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewSerive.createReview(reviewCreateRequest));
    }

    @GetMapping
    public ResponseEntity<?> getReviews() {
        return ResponseEntity.status(HttpStatus.OK).body(reviewSerive.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable long id, @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewSerive.update(id, reviewUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable long id) {
        reviewSerive.delete(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
