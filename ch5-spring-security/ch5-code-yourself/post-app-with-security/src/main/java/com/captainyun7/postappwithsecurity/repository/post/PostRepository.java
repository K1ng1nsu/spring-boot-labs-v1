package com.captainyun7.postappwithsecurity.repository.post;

import com.captainyun7.postappwithsecurity.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
    // fetch join
    @Query("""
    SELECT p FROM Post p
    LEFT JOIN FETCH p.comments
    WHERE p.id = :id
    """)
    Optional<Post> findByIdWithComments(@Param("id") Long id);
}
