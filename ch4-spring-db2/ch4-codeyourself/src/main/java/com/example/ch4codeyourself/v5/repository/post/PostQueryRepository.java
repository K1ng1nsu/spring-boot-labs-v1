package com.example.ch4codeyourself.v5.repository.post;

import com.example.ch4codeyourself.v5.domain.Post;
import com.example.ch4codeyourself.v5.dto.post.PostResponse;
import com.example.ch4codeyourself.v5.dto.post.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostQueryRepository {
    Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable);

    Page<PostResponse> search(PostSearchRequest postSearchRequest);
}
