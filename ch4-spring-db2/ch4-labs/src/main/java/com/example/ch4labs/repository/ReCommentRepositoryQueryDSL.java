package com.example.ch4labs.repository;

import com.example.ch4labs.domain.ReComment;
import com.example.ch4labs.dto.RecommentPageRequest;
import org.springframework.data.domain.Page;

public interface ReCommentRepositoryQueryDSL {
    Page<ReComment> searchRecommentByCommentId(long commentId, RecommentPageRequest recommentPageRequest);
}
