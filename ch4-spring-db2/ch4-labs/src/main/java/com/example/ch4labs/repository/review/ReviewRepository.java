package com.example.ch4labs.repository.review;

import com.example.ch4labs.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryQueryDSL {

}
