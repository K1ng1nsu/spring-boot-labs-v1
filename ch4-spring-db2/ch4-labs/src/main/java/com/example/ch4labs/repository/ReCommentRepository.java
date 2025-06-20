package com.example.ch4labs.repository;

import com.example.ch4labs.domain.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

}
