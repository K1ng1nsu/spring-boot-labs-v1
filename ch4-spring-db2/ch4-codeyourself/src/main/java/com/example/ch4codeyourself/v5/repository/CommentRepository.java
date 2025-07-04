package com.example.ch4codeyourself.v5.repository;

import com.example.ch4codeyourself.v5.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findByPostId(long postId, Pageable pageable);

    Page<Comment> findByPostIdAndParentIdNull(long postId, Pageable pageable);

    Page<Comment> findByParentId(long parentId, Pageable pageable);
}
