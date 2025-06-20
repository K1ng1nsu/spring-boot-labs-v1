package com.captainyun7.ch4examples.v5.repository.post;

import com.captainyun7.ch4examples.v5.domain.QPost;
import com.captainyun7.ch4examples.v5.domain.Post;
import com.captainyun7.ch4examples.v5.dto.post.PostResponse;
import com.captainyun7.ch4examples.v5.dto.post.PostSearchRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
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

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchByCreatedAtWithQueryDSL(LocalDateTime createdAt, Pageable pageable) {
        // Q타입 준비
        QPost post = QPost.post;

        List<Post> content = queryFactory
                .selectFrom(post)
                .where(post.createdAt.goe(createdAt))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(post.createdAt.goe(createdAt))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Page<PostResponse> search(PostSearchRequest request) {
        QPost post = QPost.post;

        // 동적 조건 조립 - BooleanBuilder
        BooleanBuilder builder = new BooleanBuilder();

        // 제목 또는 본문에 키워드 포함
        if (StringUtils.hasText(request.getKeyword())) {
            builder.and(
                    post.title.containsIgnoreCase(request.getKeyword()) // containsIgnoreCase: 대소문자 구분 없이 포함 여부 확인
                            .or(post.body.containsIgnoreCase(request.getKeyword()))
            );
        }

        // 작성자명 일치
        if (StringUtils.hasText(request.getAuthor())) {
            builder.and(post.author.eq(request.getAuthor()));
        }

        // 작성일 범위
        if (request.getStartDate() != null) {
            builder.and(post.createdAt.goe(request.getStartDate().atStartOfDay()));
        }
        if (request.getEndDate() != null) {
            builder.and(post.createdAt.loe(request.getEndDate().atTime(23, 59, 59)));
        }

        // 정렬
        OrderSpecifier<?> order = "asc".equalsIgnoreCase(request.getSort())
                ? post.createdAt.asc()
                : post.createdAt.desc();

        // 페이징
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // 데이터 조회
        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(builder)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        List<PostResponse> content = posts.stream()
                .map(PostResponse::from)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }
}
