package com.example.ch4codeyourself.v4.repository.comment;

import com.example.ch4codeyourself.v4.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findByPostId(long postId, Pageable pageable);
}
