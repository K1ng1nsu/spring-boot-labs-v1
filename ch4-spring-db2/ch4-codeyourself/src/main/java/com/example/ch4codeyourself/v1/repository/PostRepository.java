package com.example.ch4codeyourself.v1.repository;

import com.example.ch4codeyourself.v1.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
