package com.captainyun7.ch4examples.v6.service;

import com.captainyun7.ch4examples.v6.domain.Post;
import com.captainyun7.ch4examples.v6.dto.comment.CommentDto;
import com.captainyun7.ch4examples.v6.dto.comment.CommentPageResponse;
import com.captainyun7.ch4examples.v6.dto.comment.CommentResponse;
import com.captainyun7.ch4examples.v6.dto.comment.CommentSearchRequest;
import com.captainyun7.ch4examples.v6.dto.post.*;
import com.captainyun7.ch4examples.v6.repository.post.PostRepository;
import com.captainyun7.ch4examples.v6.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository repository;
    private final CommentService commentService;
    private final JdbcTemplate jdbcTemplate;

    public PostWithCommentsResponse findAllWithComments() {
        return null;
    }
    
    /**
     * (1) Lazy 전략을 사용한 게시글 전체 조회
     * 기본적으로 설정된 Lazy 로딩을 그대로 사용
     */
    @Transactional(readOnly = true)
    public List<PostWithCommentsResponse> findAllWithLazyLoading() {
        List<Post> posts = repository.findAll();
        return posts.stream()
                .map(PostWithCommentsResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * (2) BatchSize 전략을 사용한 게시글 전체 조회
     * @BatchSize 어노테이션이 적용된 상태에서 호출
     */
    @Transactional(readOnly = true)
    public List<PostWithCommentsResponse> findAllWithBatchSize() {
        List<Post> posts = repository.findAll();
        return posts.stream()
                .map(PostWithCommentsResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * (3) FetchJoin을 사용한 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PostWithCommentsResponse> findAllWithFetchJoin() {
        List<Post> posts = repository.findAllWithFetchJoin();
        return posts.stream()
                .map(PostWithCommentsResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * (4) DTO Projection을 사용한 게시글 전체 조회 - JPQL 방식
     * JPQL의 생성자 표현식을 사용하여 DTO로 직접 조회
     */
    @Transactional(readOnly = true)
    public List<PostWithCommentsResponse> findAllWithDtoProjection() {
        log.info("JPQL을 사용한 DTO Projection 조회 시작");
        
        // 1. 게시글 DTO 조회
        List<PostWithCommentsDto> postDtos = repository.findAllPostDtos();
        
        // 2. 게시글 ID 목록 추출
        List<Long> postIds = postDtos.stream()
                .map(PostWithCommentsDto::getId)
                .collect(Collectors.toList());
        
        if (postIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 3. 게시글 ID 목록으로 댓글 DTO 조회
        List<CommentDto> commentDtos = repository.findCommentDtosByPostIds(postIds);
        
        // 4. 댓글을 게시글별로 그룹화
        Map<Long, List<CommentDto>> commentsByPostId = commentDtos.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));
        
        // 5. 게시글 DTO에 댓글 설정
        for (PostWithCommentsDto postDto : postDtos) {
            List<CommentDto> comments = commentsByPostId.getOrDefault(postDto.getId(), new ArrayList<>());
            List<CommentResponse> commentResponses = comments.stream()
                    .map(CommentDto::toResponse)
                    .collect(Collectors.toList());
            postDto.setComments(commentResponses);
        }
        
        // 6. PostWithCommentsResponse로 변환
        List<PostWithCommentsResponse> result = postDtos.stream()
                .map(PostWithCommentsDto::toResponse)
                .collect(Collectors.toList());
        
        log.info("JPQL을 사용한 DTO Projection 조회 완료: {} 개의 게시글, {} 개의 댓글", 
                postDtos.size(), commentDtos.size());
        
        return result;
    }
}