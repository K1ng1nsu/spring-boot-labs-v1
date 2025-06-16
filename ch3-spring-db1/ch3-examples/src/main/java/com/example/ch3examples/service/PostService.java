package com.example.ch3examples.service;

import com.example.ch3examples.domain.Post;
import com.example.ch3examples.dto.*;
import com.example.ch3examples.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = request.toDomain();
        postMapper.save(post);
        return PostResponse.from(post);
    }

//    public List<PostResponse> getAllPosts() {
//        return postMapper.findAll().stream()
//                .map(post -> PostResponse.from(post))
//                .toList();
//    }

    public PostResponse getPostById(Long id) {
        return Optional.ofNullable(postMapper.findById(id))
                .map(PostResponse::from)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postMapper.findById(id);
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        int updated = postMapper.update(post);
        if (updated == 0) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다.");
        }

        return PostResponse.from(post);
    }

    public void deletePost(Long id) {
        int deleted = postMapper.deleteById(id);
        if (deleted == 0) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다.");
        }
    }

    public PostPageResponse getPosts(PostSearchRequest search) {

        List<PostResponse> posts = postMapper.findAll(search).stream()
                .map(PostResponse::from)
                .toList();
        int count = postMapper.count(search);

        return PostPageResponse.from(posts, search, count);
    }
}