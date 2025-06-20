package com.captainyun7.ch4examples.v6.controller;

import com.captainyun7.ch4examples.v6.dto.post.PostWithCommentsResponse;
import com.captainyun7.ch4examples.v6.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v6/fetch-test")
@Slf4j
public class FetchingTestController {

    private final PostService service;

    /**
     * (1) Lazy 전략을 사용한 게시글 전체 조회
     * - 게시글을 먼저 조회한 후, 댓글을 필요할 때 추가로 조회
     * - N+1 문제 발생 가능성 있음
     */
    @GetMapping("/lazy")
    public ResponseEntity<List<PostWithCommentsResponse>> findAllWithLazyLoading() {
        long startTime = System.currentTimeMillis();
        List<PostWithCommentsResponse> result = service.findAllWithLazyLoading();
        long endTime = System.currentTimeMillis();
        
        log.info("Lazy 로딩 실행 시간: {}ms", endTime - startTime);
        return ResponseEntity.ok(result);
    }
    
    /**
     * (2) BatchSize 전략을 사용한 게시글 전체 조회
     * - @BatchSize 어노테이션을 사용하여 한 번에 여러 댓글을 조회
     * - N+1 문제를 완화하지만 완전히 해결하지는 않음
     */
    @GetMapping("/batch-size")
    public ResponseEntity<List<PostWithCommentsResponse>> findAllWithBatchSize() {
        long startTime = System.currentTimeMillis();
        List<PostWithCommentsResponse> result = service.findAllWithBatchSize();
        long endTime = System.currentTimeMillis();
        
        log.info("BatchSize 로딩 실행 시간: {}ms", endTime - startTime);
        return ResponseEntity.ok(result);
    }
    
    /**
     * (3) FetchJoin을 사용한 게시글 전체 조회
     * - 게시글과 댓글을 한 번의 쿼리로 함께 조회
     * - N+1 문제 해결, 데이터 중복 발생 가능성 있음
     */
    @GetMapping("/fetch-join")
    public ResponseEntity<List<PostWithCommentsResponse>> findAllWithFetchJoin() {
        long startTime = System.currentTimeMillis();
        List<PostWithCommentsResponse> result = service.findAllWithFetchJoin();
        long endTime = System.currentTimeMillis();
        
        log.info("FetchJoin 로딩 실행 시간: {}ms", endTime - startTime);
        return ResponseEntity.ok(result);
    }
    
    /**
     * (4) DTO Projection을 사용한 게시글 전체 조회
     * - JPQL의 생성자 표현식을 사용하여 필요한 데이터만 조회
     * - 두 번의 쿼리로 게시글과 댓글을 각각 조회 후 애플리케이션에서 조합
     * - 메모리 사용량 최적화, 필요한 데이터만 선택적 조회
     */
    @GetMapping("/dto-projection")
    public ResponseEntity<List<PostWithCommentsResponse>> findAllWithDtoProjection() {
        long startTime = System.currentTimeMillis();
        List<PostWithCommentsResponse> result = service.findAllWithDtoProjection();
        long endTime = System.currentTimeMillis();
        
        log.info("DTO Projection 로딩 실행 시간: {}ms", endTime - startTime);
        return ResponseEntity.ok(result);
    }
} 