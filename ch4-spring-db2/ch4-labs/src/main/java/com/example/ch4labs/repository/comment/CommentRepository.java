package com.example.ch4labs.repository.comment;

import com.example.ch4labs.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryQueryDSL {
    Page<Comment> findByReviewId(long reviewId, Pageable pageable);
}
