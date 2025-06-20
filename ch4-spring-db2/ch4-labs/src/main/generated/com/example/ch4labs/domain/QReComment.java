package com.example.ch4labs.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReComment is a Querydsl query type for ReComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReComment extends EntityPathBase<ReComment> {

    private static final long serialVersionUID = -661294580L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReComment reComment = new QReComment("reComment");

    public final StringPath author = createString("author");

    public final QComment comment;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QReComment(String variable) {
        this(ReComment.class, forVariable(variable), INITS);
    }

    public QReComment(Path<? extends ReComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReComment(PathMetadata metadata, PathInits inits) {
        this(ReComment.class, metadata, inits);
    }

    public QReComment(Class<? extends ReComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment")) : null;
    }

}

