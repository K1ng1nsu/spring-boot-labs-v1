//package com.example.ch4codeyourself.v2.service;
//
//import com.example.ch4codeyourself.v2.domain.Post;
//import com.example.ch4codeyourself.v2.dto.PostCreateRequest;
//import com.example.ch4codeyourself.v2.dto.PostPageResponse;
//import com.example.ch4codeyourself.v2.dto.PostResponse;
//import com.example.ch4codeyourself.v2.dto.PostSearchRequest;
//import com.example.ch4codeyourself.v2.repository.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class PostServiceTest {
//
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Test
//    void 게시글_생성_조회_성공() {
//        //given
//        PostCreateRequest postCreateRequest = new PostCreateRequest();
//        postCreateRequest.setTitle("test title");
//        postCreateRequest.setBody("test body");
//
//        //when
//
//        PostResponse saved = postService.createPost(postCreateRequest);
//
//        PostResponse found = postService.getPostById(saved.getId());
//
//        //then
//
//        assertNotNull(found);
//        assertNotNull(saved);
//
//
//        assertEquals(saved.getId(), found.getId());
//        assertEquals(saved.getTitle(), found.getTitle());
//        assertEquals(saved.getBody(), found.getBody());
//
//    }
//
//    @Test
//    void getAllPosts() {
//        //given
//
//        // sample data
//        for(int i = 1 ; i <= 20; i++) {
//            postRepository.save(new Post(null,"게시글 "+i,"body "+i));
//        }
//
//        PostSearchRequest request = new PostSearchRequest("게시글",0,10);
//
//        //when
//
//        PostPageResponse response = postService.getAllPosts(request);
//
//        //then
//        assertNotNull(response);
//
//        assertEquals(0, response.getPage());
//        assertEquals(10, response.getSize());
//        assertEquals(20, response.getTotalCount());
//        assertEquals(2, response.getTotalPages());
//        assertEquals(10, response.getPosts().size());
//
//
//
//
//    }
//}