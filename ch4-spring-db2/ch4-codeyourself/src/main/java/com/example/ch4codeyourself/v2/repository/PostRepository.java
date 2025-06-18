package com.example.ch4codeyourself.v2.repository;

import com.example.ch4codeyourself.v2.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 자동 생성 메서드 (= JPA가 메서드명만 보고 자동으로 만들어줌)
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);




}
