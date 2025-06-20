package com.example.ch4labs.repository.review;

import com.example.ch4labs.domain.QReview;
import com.example.ch4labs.domain.Review;
import com.example.ch4labs.dto.review.ReviewSearchRequest;
import com.example.ch4labs.enums.SortDirection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryQueryDSLImpl implements ReviewRepositoryQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> search(ReviewSearchRequest reviewSearchRequest) {

        QReview review = QReview.review;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(reviewSearchRequest.getAuthor()))
            builder.and(review.author.eq(reviewSearchRequest.getAuthor()));

        if (StringUtils.hasText(reviewSearchRequest.getBookTitle()))
            builder.and(review.bookTitle.eq(reviewSearchRequest.getBookTitle()));

        if (StringUtils.hasText(reviewSearchRequest.getBookTitleContains()))
            builder.and(review.bookTitle.containsIgnoreCase(reviewSearchRequest.getBookTitleContains()));

        if (StringUtils.hasText(reviewSearchRequest.getBookAuthor()))
            builder.and(review.bookAuthor.eq(reviewSearchRequest.getBookAuthor()));

        if (StringUtils.hasText(reviewSearchRequest.getTitleContains()))
            builder.and(review.bookTitle.containsIgnoreCase(reviewSearchRequest.getTitleContains()));

        if (StringUtils.hasText(reviewSearchRequest.getContentContains()))
            builder.and(review.bookTitle.containsIgnoreCase(reviewSearchRequest.getContentContains()));

        if (reviewSearchRequest.getRating() != null)
            builder.and(review.rating.eq(reviewSearchRequest.getRating()));

        if (reviewSearchRequest.getMinRating() != null)
            builder.and(review.rating.goe(reviewSearchRequest.getMinRating()));

        if (reviewSearchRequest.getMaxRating() != null)
            builder.and(review.rating.loe(reviewSearchRequest.getMaxRating()));

        OrderSpecifier<?> orderSpecifier = review.id.desc();

        if (reviewSearchRequest.getReviewSort() != null && reviewSearchRequest.getSortDirection() != null) {
            System.out.println("################################");
            System.out.println(reviewSearchRequest.getReviewSort());
            System.out.println(reviewSearchRequest.getSortDirection());
            System.out.println("################################");
            if (reviewSearchRequest.getSortDirection() == SortDirection.ASC) {
                switch (reviewSearchRequest.getReviewSort()) {
                    case CREATED -> orderSpecifier = review.id.asc();
                    case TITLE -> orderSpecifier = review.title.asc();
                    case CONTENT -> orderSpecifier = review.content.asc();
                    case AUTHOR -> orderSpecifier = review.author.asc();
                    case BOOK_TITLE -> orderSpecifier = review.bookTitle.asc();
                    case BOOK_AUTHOR -> orderSpecifier = review.bookAuthor.asc();
                    case RATING -> orderSpecifier = review.rating.asc();
                }
            } else if (reviewSearchRequest.getSortDirection() == SortDirection.DESC) {
                switch (reviewSearchRequest.getReviewSort()) {
                    case CREATED -> orderSpecifier = review.id.desc();
                    case TITLE -> orderSpecifier = review.title.desc();
                    case CONTENT -> orderSpecifier = review.content.desc();
                    case AUTHOR -> orderSpecifier = review.author.desc();
                    case BOOK_TITLE -> orderSpecifier = review.bookTitle.desc();
                    case BOOK_AUTHOR -> orderSpecifier = review.bookAuthor.desc();
                    case RATING -> orderSpecifier = review.rating.desc();
                }
            }
        }

        Pageable pageable = PageRequest.of(reviewSearchRequest.getPage(), reviewSearchRequest.getSize());


        List<Review> fetch = jpaQueryFactory.selectFrom(review)
                .where(builder)
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = jpaQueryFactory
                .select(review.count())
                .from(review).
                where(builder)
                .fetchOne();


        return new PageImpl<>(fetch, pageable, count == null ? 0 : count);
    }
}
