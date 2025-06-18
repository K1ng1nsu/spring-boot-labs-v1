package com.example.ch4codeyourself.v3.repository;

import com.example.ch4codeyourself.v3.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
    // 자동 생성 메서드 (= JPA가 메서드명만 보고 자동으로 만들어줌)
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);


    // 작성자 일지
    // findBy[field][condition]And|OR[field][condition][sort]

    Page<Post> findByAuthor(String author, Pageable pageable);

    Page<Post> findByTitleContainsAndAuthor(String title, String author, Pageable pageable);

    Page<Post> findByCreatedAtAfter(LocalDateTime createdAt, Pageable pageable);

    // JPQL
    @Query("SELECT p FROM Post p WHERE p.title like %:title% AND p.author = :author ")
    Page<Post> searchByAuthorAndTitle(@Param("author") String author, String title, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.createdAt >= :createdAt")
    Page<Post> searchByCreatedAtAfter(@Param("createdAt") LocalDateTime createdAt, Pageable pageable);

}
