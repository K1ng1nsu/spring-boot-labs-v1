package com.captainyun7.ch4examples.v2;

import com.captainyun7.ch4examples.v2.domain.Post;
import com.captainyun7.ch4examples.v2.dto.*;
import com.captainyun7.ch4examples.v2.repository.PostRepository;
import com.captainyun7.ch4examples.v2.service.PostService;
import com.captainyun7.ch4examples.v2.dto.PostPageResponse;
import com.captainyun7.ch4examples.v2.dto.PostSearchRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
@SpringBootTest
@Transactional // 각 테스트 후 롤백됨 (테스트 간 독립성 보장)
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 생성 후 단건 조회하면 동일한 내용이 반환된다")
    void 게시글_생성_후_조회() {
        // given
        PostCreateRequest request = new PostCreateRequest("테스트 제목", "테스트 본문");

        // when
        PostResponse saved = postService.createPost(request);
        PostResponse found = postService.getPostById(saved.getId());

        // then
        assertThat(found.getTitle()).isEqualTo("테스트 제목");
        assertThat(found.getBody()).isEqualTo("테스트 본문");
    }

    @Test
    @DisplayName("게시글을 수정하면 내용이 변경되어 반영된다")
    void 게시글_수정_정상작동() {
        // given
        Post saved = postRepository.save(new Post(null, "원래 제목", "원래 본문"));

        PostUpdateRequest updateRequest = new PostUpdateRequest("바뀐 제목", "바뀐 본문");

        // when
        PostResponse updated = postService.updatePost(saved.getId(), updateRequest);
        PostResponse found = postService.getPostById(saved.getId());

        // then
        assertThat(updated.getTitle()).isEqualTo("바뀐 제목");
        assertThat(found.getTitle()).isEqualTo("바뀐 제목");
    }

    @Test
    @DisplayName("게시글을 삭제하면 조회 시 예외가 발생한다")
    void 게시글_삭제_정상작동() {
        // given
        Post saved = postRepository.save(new Post(null, "삭제할 제목", "내용"));

        // when
        postService.deletePost(saved.getId());

        // then
        assertThatThrownBy(() -> postService.getPostById(saved.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("검색어를 기준으로 게시글을 페이징 조회할 수 있다")
    void 게시글_검색_페이징() {
        // given
        for (int i = 1; i <= 15; i++) {
            postRepository.save(new Post(null, "JPA 게시글 " + i, "내용"));
        }

        PostSearchRequest request = new PostSearchRequest("JPA", 0, 10); // page 0, size 10

        // when
        PostPageResponse response = postService.search(request);

        // then
        assertThat(response.getPage()).isEqualTo(0);
        assertThat(response.getSize()).isEqualTo(10);
        assertThat(response.getTotalCount()).isEqualTo(15);
        assertThat(response.getPosts()).hasSize(10);
        assertThat(response.getPosts().get(0).getTitle()).startsWith("JPA 게시글");
    }
}



