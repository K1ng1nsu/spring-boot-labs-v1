package com.captainyun7.ch4examples.v1.service;

import com.captainyun7.ch4examples.v1.domain.Post;
import com.captainyun7.ch4examples.v1.dto.*;
import com.captainyun7.ch4examples.v1.repository.PostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Disabled
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 시 저장 후 응답값이 정상적으로 반환된다")
    void 게시글_생성_성공() {
        // given
        PostCreateRequest request = new PostCreateRequest("제목", "내용");
        Post savedPost = new Post(1L, "제목", "내용");

        given(postRepository.save(any(Post.class)))
                .willReturn(savedPost);

        // when
        PostResponse result = postService.createPost(request);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("제목");

        // optional: 저장 요청이 한 번 호출되었는지 검증
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("존재하는 ID로 게시글을 조회하면 정상적으로 응답한다")
    void 게시글_단건_조회_성공() {
        // Given
        Post post = new Post(1L, "첫번째 게시글", "테스트 내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // When
        PostResponse result = postService.getPostById(1L);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("첫번째 게시글");
        assertThat(result.getBody()).isEqualTo("테스트 내용");
    }

    @Test
    @DisplayName("게시글 수정 요청 시, 기존 게시글을 찾아 수정하고 정상적으로 응답한다")
    void 게시글_수정_성공() {
        // Given
        Long id = 1L;
        Post existingPost = new Post(id, "기존 제목", "기존 내용");
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        // repository.findById() 호출 시 기존 post 반환
        given(postRepository.findById(id)).willReturn(Optional.of(existingPost));

        // 수정 후 save(post) 호출 시 저장된 post 반환
        given(postRepository.save(existingPost)).willReturn(existingPost); // 실제로는 영속 상태라 자기 자신이 반환됨

        // When
        PostResponse result = postService.updatePost(id, request);

        // Then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo("수정된 제목");
        assertThat(result.getBody()).isEqualTo("수정된 내용");

        verify(postRepository).findById(id);
        verify(postRepository).save(existingPost);
    }

    @Test
    @DisplayName("게시글 삭제 요청 시, deleteById가 정상적으로 호출된다")
    void 게시글_삭제_성공() {
        // Given
        Long id = 1L;

        // When
        postService.deletePost(id);

        // Then
        verify(postRepository).deleteById(id);
    }

//    @Test
//    void 검색어로_게시글_조회_페이징_정상() {
//        // given
//        PostSearchRequest request = new PostSearchRequest("JPA", 0, 10);
//        Pageable pageable = PageRequest.of(0, 10);
//
//        List<Post> postList = List.of(
//                new Post(null, "JPA란?", "내용1"),
//                new Post(null, "JPA 활용법", "내용2")
//        );
//        Page<Post> postPage = new PageImpl<>(postList, pageable, 2);
//
//        given(postRepository.findByTitleContaining("JPA", pageable))
//                .willReturn(postPage);
//
//        // when
//        PostPageResponse result = postService.search(request);
//
//        // then
//        assertThat(result.getPage()).isEqualTo(0);
//        assertThat(result.getSize()).isEqualTo(10);
//        assertThat(result.getTotalCount()).isEqualTo(2);
//        assertThat(result.getPosts()).hasSize(2);
//        assertThat(result.getPosts().get(0).getTitle()).isEqualTo("JPA란?");
//    }
}