package com.captainyun7.ch4examples.v3.repository;

import com.captainyun7.ch4examples.v3.dto.PostResponse;
import com.captainyun7.ch4examples.v3.domain.Post;
import com.captainyun7.ch4examples.v3.dto.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostQueryRepository {
    Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable);
    Page<PostResponse> search(PostSearchRequest request);
}
