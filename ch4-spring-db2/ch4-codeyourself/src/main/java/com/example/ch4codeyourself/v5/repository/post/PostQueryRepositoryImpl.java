package com.example.ch4codeyourself.v5.repository.post;

import com.example.ch4codeyourself.v5.domain.Post;
import com.example.ch4codeyourself.v5.domain.QPost;
import com.example.ch4codeyourself.v5.dto.post.PostResponse;
import com.example.ch4codeyourself.v5.dto.post.PostSearchRequest;
import com.example.ch4codeyourself.v5.repository.post.PostQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable) {
        QPost post = QPost.post;

        List<Post> fetch = jpaQueryFactory.
                selectFrom(post)
                .where(post.createdAt.goe(createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(post.createdAt.goe(createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(fetch, pageable, count != null ? count : 0);
    }


    @Override
    public Page<PostResponse> search(PostSearchRequest postSearchRequest) {
        QPost post = QPost.post;

        // BooleanBuilder
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if (StringUtils.hasText(postSearchRequest.getKeyword())) {
            booleanBuilder.and(
                    post.title.containsIgnoreCase(postSearchRequest.getKeyword())
                            .or(post.body.containsIgnoreCase(postSearchRequest.getKeyword()))
            );
        }

        if (StringUtils.hasText(postSearchRequest.getAuthor())) {
            booleanBuilder.and(post.author.eq(postSearchRequest.getAuthor()));
        }

        if (postSearchRequest.getCreatedAt() != null) {
            booleanBuilder.and(post.createdAt.goe(postSearchRequest.getCreatedAt()));
        }

        Pageable pageable = PageRequest.of(postSearchRequest.getPage(), postSearchRequest.getSize());

        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .where(booleanBuilder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(booleanBuilder)
                .fetchOne();


        List<PostResponse> postResponseList = posts.stream().map(PostResponse::from).toList();


        return new PageImpl<>(postResponseList, pageable, count != null ? count : 0);
    }
}
