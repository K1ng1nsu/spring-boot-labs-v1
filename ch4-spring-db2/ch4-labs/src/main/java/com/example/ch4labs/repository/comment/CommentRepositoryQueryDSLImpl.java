package com.example.ch4labs.repository.comment;

import com.example.ch4labs.domain.Comment;
import com.example.ch4labs.domain.QComment;
import com.example.ch4labs.dto.comment.CommentSearchRequest;
import com.example.ch4labs.enums.SortDirection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryQueryDSLImpl implements CommentRepositoryQueryDSL {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> searchCommentByReviewId(CommentSearchRequest commentSearchRequest, long reviewId) {

        QComment comment = QComment.comment;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(comment.reviewId.eq(reviewId));

        OrderSpecifier<?> orderSpecifier = comment.id.desc();

        if (commentSearchRequest.getSortDirection() != null && commentSearchRequest.getCommentSort() != null) {
            if (commentSearchRequest.getSortDirection() == SortDirection.ASC) {
                switch (commentSearchRequest.getCommentSort()) {
                    case ID -> orderSpecifier = comment.id.asc();
                    case CONTENT -> orderSpecifier = comment.content.asc();
                    case AUTHOR -> orderSpecifier = comment.author.asc();
                    case REVIEW_ID -> orderSpecifier = comment.reviewId.asc();
                    case CREATED_AT -> orderSpecifier = comment.createdAt.asc();
                    case UPDATED_AT -> orderSpecifier = comment.updatedAt.asc();
                }
            } else if (commentSearchRequest.getSortDirection() == SortDirection.DESC) {
                switch (commentSearchRequest.getCommentSort()) {
                    case ID -> orderSpecifier = comment.id.desc();
                    case CONTENT -> orderSpecifier = comment.content.desc();
                    case AUTHOR -> orderSpecifier = comment.author.desc();
                    case REVIEW_ID -> orderSpecifier = comment.reviewId.desc();
                    case CREATED_AT -> orderSpecifier = comment.createdAt.desc();
                    case UPDATED_AT -> orderSpecifier = comment.updatedAt.desc();
                }
            }
        }

        Pageable pageable = PageRequest.of(commentSearchRequest.getPage(), commentSearchRequest.getSize());

        List<Comment> fetch = queryFactory.selectFrom(comment)
                .where(builder)
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        System.out.println("###########################");
        System.out.println("###########################");
        System.out.println(fetch);
        System.out.println("###########################");
        System.out.println("###########################");


        Long totalElements = queryFactory.select(comment.count())
                .from(comment)
                .where(builder)
                .fetchOne();


        return new PageImpl<>(fetch, pageable, totalElements != null ? totalElements : 0);
    }
}
