package com.captainyun7.ch4examples.v6.config;

import com.captainyun7.ch4examples.v6.domain.Comment;
import com.captainyun7.ch4examples.v6.domain.Post;
import com.captainyun7.ch4examples.v6.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final PostRepository postRepository;
    private final Random random = new Random();

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // 기존 데이터가 있는지 확인
        if (postRepository.count() > 0) {
            log.info("데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("테스트 데이터 생성을 시작합니다...");
        
        // 게시글 100개 생성
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            Post post = new Post();
            post.setTitle("테스트 게시글 " + i);
            post.setBody("이것은 테스트 게시글 " + i + "의 내용입니다. 성능 테스트를 위한 데이터입니다.");
            post.setAuthor("작성자" + i);
            post.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30))); // 최근 30일 내의 랜덤 날짜
            
            // 각 게시글마다 댓글 20개 생성
            List<Comment> comments = new ArrayList<>();
            for (int j = 1; j <= 20; j++) {
                Comment comment = new Comment();
                comment.setContent("게시글 " + i + "에 대한 댓글 " + j + "입니다.");
                comment.setAuthor("댓글작성자" + j);
                comment.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)).plusHours(j)); // 게시글보다 이후 시간
                comment.setPost(post);
                comments.add(comment);
            }
            
            // 댓글 중 일부는 대댓글로 설정 (5개 정도)
            for (int j = 0; j < 5; j++) {
                int parentIndex = random.nextInt(10); // 0-9 사이의 댓글을 부모로 설정
                int childIndex = 10 + random.nextInt(10); // 10-19 사이의 댓글을 자식으로 설정
                
                if (parentIndex != childIndex && parentIndex < comments.size() && childIndex < comments.size()) {
                    comments.get(childIndex).setParent(comments.get(parentIndex));
                    comments.get(parentIndex).getChildren().add(comments.get(childIndex));
                }
            }
            
            post.setComments(comments);
            posts.add(post);
        }
        
        try {
            // 데이터베이스에 저장
            postRepository.saveAll(posts);
            
            log.info("테스트 데이터 생성이 완료되었습니다. 게시글 {}개, 댓글 {}개가 생성되었습니다.", 
                    posts.size(), posts.size() * 20);
        } catch (Exception e) {
            log.error("데이터 생성 중 오류가 발생했습니다: {}", e.getMessage());
        }
    }
} 