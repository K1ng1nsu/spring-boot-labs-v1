package com.captainyun7.ch4examples.v5.repository.comment;

import com.captainyun7.ch4examples.v5.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    
    // 부모 댓글만 페이징 처리하여 조회 (parent가 null인 댓글)
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parent IS NULL")
    Page<Comment> findParentCommentsByPostId(@Param("postId") Long postId, Pageable pageable);
    
    // 특정 부모 댓글의 모든 자식 댓글 조회
    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentId);
}
