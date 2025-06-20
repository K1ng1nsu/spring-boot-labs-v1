package com.captainyun7.ch4examples.v5.repository.post;

import com.captainyun7.ch4examples.v5.domain.Post;
import com.captainyun7.ch4examples.v5.dto.post.PostResponse;
import com.captainyun7.ch4examples.v5.dto.post.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostQueryRepository {
    Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable);
    Page<PostResponse> search(PostSearchRequest request);
}
