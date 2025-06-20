package com.example.ch4codeyourself.v3.repository;

import com.example.ch4codeyourself.v3.domain.Post;
import com.example.ch4codeyourself.v3.dto.PostResponse;
import com.example.ch4codeyourself.v3.dto.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostQueryRepository {
    Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable);

    Page<PostResponse> search(PostSearchRequest postSearchRequest);
}
