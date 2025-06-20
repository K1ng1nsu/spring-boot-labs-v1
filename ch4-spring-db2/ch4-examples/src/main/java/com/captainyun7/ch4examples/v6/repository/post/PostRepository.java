package com.captainyun7.ch4examples.v6.repository.post;

import com.captainyun7.ch4examples.v6.domain.Post;
import com.captainyun7.ch4examples.v6.dto.post.PostWithCommentsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // FetchJoin을 사용한 전체 게시글 조회
    @Query("""
    SELECT DISTINCT p FROM Post p
    LEFT JOIN FETCH p.comments
    """)
    List<Post> findAllWithFetchJoin();
    
    // fetch join으로 단일 게시글 조회
    @Query("""
    SELECT p FROM Post p
    LEFT JOIN FETCH p.comments
    WHERE p.id = :id
    """)
    Optional<Post> findByIdWithComments(@Param("id") Long id);
    
    // JPQL을 사용한 DTO Projection
    @Query("""
    SELECT new com.captainyun7.ch4examples.v6.dto.post.PostWithCommentsDto(
        p.id, p.title, p.body, p.author, p.createdAt
    )
    FROM Post p
    """)
    List<PostWithCommentsDto> findAllPostDtos();
    
    // 게시글 ID로 댓글 DTO 조회
    @Query("""
    SELECT new com.captainyun7.ch4examples.v6.dto.comment.CommentDto(
        c.id, p.id, c.content, c.author, c.createdAt
    )
    FROM Comment c
    JOIN c.post p
    WHERE p.id IN :postIds
    """)
    List<com.captainyun7.ch4examples.v6.dto.comment.CommentDto> findCommentDtosByPostIds(@Param("postIds") List<Long> postIds);
}
