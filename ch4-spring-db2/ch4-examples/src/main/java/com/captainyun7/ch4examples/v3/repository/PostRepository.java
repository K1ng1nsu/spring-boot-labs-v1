package com.captainyun7.ch4examples.v3.repository;

import com.captainyun7.ch4examples.v3.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
    // 제목에 검색어 포함
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
    // 작성자와 검색어 일치
    Page<Post> findByAuthor(String author, Pageable pageable);
    // 제목에 검색어 포함 + 작성자와 검색어 일치
    Page<Post> findByAuthorAndTitleContaining(String author, String title, Pageable pageable);
    // 작성일자 이후
    Page<Post> findByCreatedAtAfter(LocalDateTime createdAt, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.author = :author AND p.title LIKE %:title%")
    Page<Post> searchByAuthorAndTitle(@Param("author") String author, @Param("title") String title, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.createdAt >= :createdAt")
    Page<Post> searchByCreatedAfter(@Param("createdAt") LocalDateTime createdAt, Pageable pageable);
}
