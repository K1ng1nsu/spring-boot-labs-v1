package com.captainyun7.ch4examples.v1.service;

import com.captainyun7.ch4examples.v1.domain.Post;
import com.captainyun7.ch4examples.v1.dto.PostPageResponse;
import com.captainyun7.ch4examples.v1.dto.PostSearchRequest;
import com.captainyun7.ch4examples.v1.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void 검색어로_게시글_조회_페이징_정상() {
        // given
        PostSearchRequest request = new PostSearchRequest("JPA", 0, 10);
        Pageable pageable = PageRequest.of(0, 10);

        List<Post> postList = List.of(
                new Post(null, "JPA란?", "내용1"),
                new Post(null, "JPA 활용법", "내용2")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, 2);

        given(postRepository.findByTitleContaining("JPA", pageable))
                .willReturn(postPage);

        // when
        PostPageResponse result = postService.search(request);

        // then
        assertThat(result.getPage()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalCount()).isEqualTo(2);
        assertThat(result.getPosts()).hasSize(2);
        assertThat(result.getPosts().get(0).getTitle()).isEqualTo("JPA란?");
    }
}